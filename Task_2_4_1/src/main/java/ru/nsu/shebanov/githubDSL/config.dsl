tasks {
    task(id: 1, name: "Task_1_1_1", maxPoints: 1, softDeadline: "2024-09-01", hardDeadline: "2024-09-10")
    task(id: 2, name: "Task_1_1_2", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 3, name: "Task_1_1_3", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 4, name: "Task_1_2_1", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 5, name: "Task_1_2_2", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 6, name: "Task_1_3_1", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 7, name: "Task_1_4_1", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 8, name: "Task_2_1_1", maxPoints: 1, softDeadline: "2024-09-01", hardDeadline: "2024-09-10")
    task(id: 9, name: "Task_2_1_2", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 10, name: "Task_2_2_1", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 11, name: "Task_2_3_1", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
    task(id: 12, name: "Task_2_4_1", maxPoints: 1, softDeadline: "2024-10-01", hardDeadline: "2024-10-15")
};

students {
    student(nickname: "filimonq", name: "Ildarik", repository: "https://github.com/filimonq/OOP")
};

groups {
    group(name: "23215", students: ["filimonq"])
};

config {
    downloadFolder : "D:\Z\Programming\_Univeristy\NSU\OOP\DSL"
};