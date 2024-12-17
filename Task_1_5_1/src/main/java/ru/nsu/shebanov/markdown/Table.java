package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Table {
    public static final int ALIGN_LEFT = -1;
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_RIGHT = 1;


    public static class TableElement extends Element{
        List<List<String>> items;
        List<Integer> width;
        List<Integer> alignments;


        public TableElement(List<Integer> width, List<List<String>> items, List<Integer> alignments) {
            this.items = items;
            this.width = width;
            this.alignments = alignments;
        }



        private String expandString(String str, int width) {
            return str + " ".repeat(width - str.length());
        }

        private String dashDelimiter(int alignment, int width) {
            if(alignment == -1) {
                return ":" + "-".repeat(width-1);
            } else if(alignment == 1){
                return "-".repeat(width-1) + ":";
            } else {
                return ":"+"-".repeat(width-2)+":";
            }
        }

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
                    if(line.get(i).contains("\n")) {
                        sb.append(line.get(i).replace("\n", "<br>"));
                    }
                    else {
                        sb.append(expandString(line.get(i), this.width.get(i)));
                    }
                    sb.append(" |");
                }
            }

            System.out.println("Actually there");
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TableElement)) return false;
            TableElement that = (TableElement) o;
            return Objects.equals(items, that.items)
                    && Objects.equals(width, that.width)
                    && Objects.equals(alignments, that.alignments);
        }

        @Override
        public int hashCode() {
            return Objects.hash(items, width, alignments);
        }
    }


    public static class TableBuilder {
        List<List<String>> elements;
        List<Integer> width;
        List<Integer> alignments;
        int limit = -1;

        public TableBuilder() {
            this.elements = new ArrayList<>();
            this.width = new ArrayList<>();
            this.alignments = new ArrayList<>();
        }

        public TableBuilder withAlignments(Integer...  alignments) {
            this.alignments = new ArrayList<>(Arrays.asList(alignments));

            int widthLength = this.width.size();
            for(int i = widthLength; i < this.alignments.size(); i++) {
                this.width.add(5);
            }

            return this;
        }

        public TableBuilder withRowLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public TableBuilder addRow(List<String> row){
            if(this.elements.size() == this.limit) {
                throw new IllegalArgumentException("Limit is achieved");
            }

            this.elements.add(row);
            int widthLength = this.width.size();
            for(int i = widthLength; i < row.size(); i++) {
                this.width.add(5);
            }

            return this;
        }

        /**
         * IDIOTIC FORM OF SUPPORTING EVERYTHING IN THE WORLD
         */
        public TableBuilder addRow(Object... row){
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

        public TableElement build() {
            int tableWidth = this.width.size();

            for(int i = this.alignments.size(); i < tableWidth; i++) {
                this.alignments.add(-1);
            }

            for(List<String> row: this.elements) {
                for(int i = row.size(); i < tableWidth; i++) {
                    row.add("");
                }
            }

            for(int i = 0; i < tableWidth; i++) {
                for (List<String> row: this.elements) {
                    this.width.set(i, Integer.max(this.width.get(i), stringWidth(row.get(i))));
                }
            }

            return new TableElement(this.width, this.elements, this.alignments);
        }
    }
}
