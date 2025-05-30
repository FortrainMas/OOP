grammar CourseDSL;

@header {
    package ru.nsu.shebanov.githubdsl.antlr;
}

course    : tasks ';' students ';' groups ';' config ';';
tasks     : 'tasks' '{' task+ '}';
task      : 'task' '(' 'id' ':' INT ',' 'name' ':' STRING ',' 'maxPoints' ':' INT ',' 'softDeadline' ':' STRING ',' 'hardDeadline' ':' STRING ')';
students  : 'students' '{' student+ '}';
student   : 'student' '(' 'nickname' ':' STRING ',' 'name' ':' STRING ',' 'repository' ':' STRING ')';
groups    : 'groups' '{' group+ '}';
group     : 'group' '(' 'name' ':' STRING ',' 'students' ':' '[' STRING (',' STRING)* ']' ')';
config    : 'config' '{' 'downloadFolder' ':' STRING '}';

STRING    : '"' ( ~["] | '\\' . )* '"';
INT       : [0-9]+;
WS        : [ \t\r\n]+ -> skip;