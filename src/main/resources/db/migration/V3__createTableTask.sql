CREATE TABLE Task (
	id bigint(20)  NOT NULL AUTO_INCREMENT,
	createdAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	statement varchar(255) NOT NULL,
	orderNumber int NOT NULL,
	type enum('OPEN_TEXT','SINGLE_CHOICE', 'MULTIPLE_CHOICE')
		CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
	course_id bigint(20) NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT FK_Task_Course FOREIGN KEY (course_id)
		REFERENCES Course(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
		
CREATE TABLE TaskOption (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    optionText varchar(80) NOT NULL,
    isCorrect bit(1) NOT NULL,
    task_id bigint(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_TaskOption_Task FOREIGN KEY (task_id)
        REFERENCES Task(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

