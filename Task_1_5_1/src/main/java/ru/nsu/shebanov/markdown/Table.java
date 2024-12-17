package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Markdown class for table.
 */
public class Table {
    public static final int ALIGN_LEFT = -1;
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_RIGHT = 1;

    /**
     * Markdown element table.
     */
    public static class TableElement extends Element {
        public List<List<String>> items;
        public List<Integer> width;
        public List<Integer> alignments;


        /**
         * Produce table element in-place.
         *
         * @param width width of each column
         * @param items items as rows.
         * @param alignments alignments of columns
         */
        public TableElement(List<Integer> width,
                            List<List<String>> items, List<Integer> alignments) {
            this.items = items;
            this.width = width;
            this.alignments = alignments;
        }

        private String expandString(String str, int width) {
            return str + " ".repeat(width - str.length());
        }

        private String dashDelimiter(int alignment, int width) {
            if (alignment == -1) {
                return ":" + "-".repeat(width - 1);
            } else if (alignment == 1) {
                return "-".repeat(width - 1) + ":";
            } else {
                return ":" + "-".repeat(width - 2) + ":";
            }
        }

        /**
         * Get markdown representation of table.
         *
         * @return string
         */
        @Override
        public String toString() {
            int tableWidth = this.width.size();

            // Add line of headers
            StringBuilder sb = new StringBuilder();
            sb.append("|");
            for (int i = 0; i < tableWidth; i++) {
                sb.append(" ");
                sb.append(expandString(this.items.get(0).get(i), this.width.get(i)));
                sb.append(" |");
            }
            this.items.remove(0);

            // Add line of delimiters
            sb.append("\n|");
            for (int i = 0; i < tableWidth; i++) {
                sb.append(" ");
                sb.append(dashDelimiter(this.alignments.get(i), this.width.get(i)));
                sb.append(" |");
            }

            // Add table content
            for (List<String> line : this.items) {
                sb.append("\n|");
                for (int i = 0; i < tableWidth; i++) {
                    sb.append(" ");
                    if (line.get(i).contains("\n")) {
                        sb.append(line.get(i).replace("\n", "<br>"));
                    } else {
                        sb.append(expandString(line.get(i), this.width.get(i)));
                    }
                    sb.append(" |");
                }
            }

            return sb.toString();
        }

        /**
         * Test for equality.
         *
         * @param o object to compare with
         * @return true if something, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TableElement)) {
                return false;
            }
            TableElement that = (TableElement) o;
            return Objects.equals(items, that.items)
                    && Objects.equals(width, that.width)
                    && Objects.equals(alignments, that.alignments);
        }

        /**
         * Hashcode of table.
         *
         * @return value
         */
        @Override
        public int hashCode() {
            return Objects.hash(items, width, alignments);
        }
    }


    /**
     * Builder for table.
     */
    public static class TableBuilder {
        public List<List<String>> elements;
        public List<Integer> width;
        public List<Integer> alignments;
        int limit = -1;

        /**
         * Produce new table builder.
         */
        public TableBuilder() {
            this.elements = new ArrayList<>();
            this.width = new ArrayList<>();
            this.alignments = new ArrayList<>();
        }

        /**
         * Set alignments.
         *
         * @param alignments some sequence of numbers -1, 0, 1
         * @return itself
         */
        public TableBuilder withAlignments(Integer... alignments) {
            this.alignments = new ArrayList<>(Arrays.asList(alignments));

            int widthLength = this.width.size();
            for (int i = widthLength; i < this.alignments.size(); i++) {
                this.width.add(5);
            }

            return this;
        }

        /**
         * Set row limit.
         *
         * @param limit limit
         * @return itself
         */
        public TableBuilder withRowLimit(int limit) {
            this.limit = limit;
            return this;
        }

        /**
         * Add new row to table.
         *
         * @param row new row
         * @return itself
         */
        public TableBuilder addRow(List<String> row) {
            if (this.elements.size() == this.limit) {
                throw new IllegalArgumentException("Limit is achieved");
            }

            this.elements.add(row);
            int widthLength = this.width.size();
            for (int i = widthLength; i < row.size(); i++) {
                this.width.add(5);
            }

            return this;
        }

        /**
         * add new row.
         *
         * @param row arbitrary values
         * @return itself
         */
        public TableBuilder addRow(Object... row) {
            List<Object> rowObject = new ArrayList<>(Arrays.asList(row));

            List<String> normalRows = rowObject.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            return addRow(normalRows);
        }

        private int stringWidth(String line) {
            if (line == null || line.isEmpty()) {
                return 0;
            }

            String[] parts = line.split("\n");
            int maxWidth = 0;

            for (String part : parts) {
                maxWidth = Math.max(maxWidth, part.length());
            }

            return maxWidth;
        }

        /**
         * Build table element.
         *
         * @return Table element
         */
        public TableElement build() {
            int tableWidth = this.width.size();

            for (int i = this.alignments.size(); i < tableWidth; i++) {
                this.alignments.add(-1);
            }

            for (List<String> row : this.elements) {
                for (int i = row.size(); i < tableWidth; i++) {
                    row.add("");
                }
            }

            for (int i = 0; i < tableWidth; i++) {
                for (List<String> row : this.elements) {
                    this.width.set(i, Integer.max(this.width.get(i), stringWidth(row.get(i))));
                }
            }

            return new TableElement(this.width, this.elements, this.alignments);
        }
    }
}
