DROP DATABASE if exists CRM;
CREATE DATABASE CRM;
USE CRM;

CREATE TABLE Users(
	UserID int auto_increment primary key,
	email varchar(100),
	password varchar(100),
    Fullname varchar(255),
    Address varchar(255),
    Phone varchar(10),
    RoleID int not null,
    index idx_role(RoleID)
);

CREATE TABLE Roles(
	RoleID int auto_increment primary key,
    RoleName varchar(255),
	Description text
);

CREATE TABLE Status(
	StatusID int auto_increment primary key,
	StatusName varchar(255),
    StatusType ENUM("Project", "Task")
);

CREATE TABLE Projects (
  ProjectID INT PRIMARY KEY AUTO_INCREMENT,
  ProjectName VARCHAR(255),
  Description TEXT,
  StartDate DATE,
  EndDate DATE,
  CreatorID INT,
  StatusID INT
);

CREATE TABLE ProjectUsers(
	ProjectID int,
    UserID int,
    primary key(ProjectID, UserID)
);

CREATE TABLE Tasks(
	TaskID int auto_increment primary key,
	TaskName varchar(255),
    StartDate DATE,
	EndDate DATE,
	ExecutorID int,
    ProjectID int,
    StatusID int,
    index idx_executor(ExecutorID),
    index idx_project (ProjectID),
    index idx_status (StatusID)
);

CREATE TABLE UserTasks (
	UserID int,
	TaskID int,
	primary key(UserID, TaskID)
);

CREATE TABLE ProjectStatusHistory(
	ProjectID int,
	StatusID int,
	Timestamp datetime default current_timestamp,
	primary key(ProjectID, StatusID, Timestamp)
);

CREATE TABLE TaskStatusHistory(
	TaskID int,
	StatusID int,
	Timestamp datetime default current_timestamp,
	primary key(TaskID, StatusID, Timestamp)
);

ALTER TABLE Users ADD FOREIGN KEY(RoleID) REFERENCES Roles(RoleID);


ALTER TABLE ProjectStatusHistory ADD FOREIGN KEY(ProjectID) REFERENCES Projects(ProjectID);
ALTER TABLE ProjectStatusHistory ADD FOREIGN KEY(StatusID) REFERENCES Status(StatusID);

ALTER TABLE ProjectUsers add foreign key(ProjectID) references Projects(ProjectID);
ALTER TABLE ProjectUsers add foreign key(UserID) references Users(UserID);

ALTER TABLE UserTasks ADD FOREIGN KEY(UserID) REFERENCES Users(UserID);
ALTER TABLE UserTasks ADD FOREIGN KEY(TaskID) REFERENCES Tasks(TaskID);

ALTER TABLE Tasks ADD FOREIGN KEY(ProjectID) REFERENCES Projects(ProjectID);
ALTER TABLE TaskStatusHistory ADD FOREIGN KEY(TaskID) REFERENCES Tasks(TaskID);
ALTER TABLE TaskStatusHistory ADD FOREIGN KEY(StatusID) REFERENCES Status(StatusID);


INSERT INTO Users(email, password) VALUES ("username@gmail.com", "12345678");
INSERT INTO Users(email, password) VALUES ("password@gmail.com", "12345");
INSERT INTO Users(email, password) VALUES ("abc@gmail.com", "12345");
INSERT INTO Users(email, password,RoleID) VALUES ("abcd@gmail.com", "123456","4");

INSERT INTO Roles(RoleID, RoleName) VALUES (3, "LEADER");

SELECT RoleID FROM Roles WHERE RoleName = "ADMIN";


INSERT INTO Users(email, password, RoleID)
SELECT "newuser@gmail.com", "password123", RoleID FROM Roles WHERE RoleName = "ADMIN";


DELETE FROM Roles WHERE RoleID  = 27; 

SELECT * FROM Roles;



SELECT *
FROM Users 
JOIN Roles r on Users.RoleID = r.RoleID 
WHERE Users.email = "abcd@gmail.com" AND Users.password = "123456";

SELECT * FROM Users WHERE email = "abc@gmail.com";
SELECT * FROM Roles;

SELECT Users.UserID, Users.email, Users.password ,Users.Fullname, Users.Address, Users.Phone, Roles.RoleName
FROM Users
JOIN Roles ON Users.RoleID = Rofgfgles.RoleID

INSERT INTO Users(Fullname, email, password, Phone, Address) VALUES("", ?, ?, ?, ?);
DELETE FROM Roles WHERE RoleID = 3;

UPDATE Users SET RoleID = NULL WHERE RoleID = 3;
DELETE FROM Users WHERE RoleID = 3;

INSERT INTO Users(email, password, Fullname, Address, Phone, RoleID) 
VALUES ("Nguyễn Văn Nam", "Nam@gmail.com", "1234", "0135987459","Ha Nội",40);

SELECT RoleID, RoleName FROM Roles;

DELETE FROM Users WHERE UserID = 55;

UPDATE Users 
SET email="abcd@gmail.com", password="12345678", fullName="Nguyen Van B", address="hcm", phone="01234568", roleId=4
WHERE UserID = 26;

UPDATE Roles SET RoleName = "LEADER", Description = "Tran Minh Tuan" WHERE RoleID = "23"; 

-- Thêm project
INSERT INTO Projects(ProjectName,StartDate,EndDate) VALUES("Làm app todolist", "2023-09-15", "2023-11-25");
-- Update project 
UPDATE Projects SET ProjectName = "Dự án web bán hàng", StartDate = "2023-06-20", EndDate = "2023-11-6" WHERE ProjectID = 7;

DELETE FROM Projects WHERE ProjectID = 2;



INSERT INTO Tasks(TaskName, StartDate, EndDate, ExecutorID, ProjectID, StatusID)
VALUES("Xây dựng trang home","2023-05-06", "2023-07-25", 79, 6, 1);



SELECT * FROM Tasks;
SELECT * FROM Users;
SELECT * FROM Status;
INSERT INTO Status(StatusName, StatusType, StatusID) VALUES("Pending", "Task",1);
INSERT INTO Status(StatusName, StatusType, StatusID) VALUES("In Progress", "Task",2);
INSERT INTO Status(StatusName, StatusType, StatusID) VALUES("Completed", "Task",3);



INSERT INTO UserTasks(UserID, TaskID) VALUES(79, 1);
SELECT 
    Tasks.TaskID as 'STT', 
    Tasks.TaskName as 'Tên công việc', 
    Projects.ProjectName as 'Dự án', 
    Users.Fullname as 'Người thực hiện', 
    Tasks.StartDate as 'Ngày bắt đầu', 
    Tasks.EndDate as 'Ngày kết thúc', 
    Status.StatusName as 'Trạng thái'
FROM 
    Tasks
JOIN 
    Projects ON Tasks.ProjectID = Projects.ProjectID
JOIN 
    Users ON Tasks.ExecutorID = Users.UserID
JOIN 
    Status ON Tasks.StatusID = Status.StatusID;

INSERT INTO TaskStatusHistory(TaskID, StatusID) VALUES(1, 1);
-- SELECT t.TaskID, t.TaskName, s.StatusName
-- FROM Tasks t
-- JOIN TaskStatusHistory h ON t.TaskID = h.TaskID
-- JOIN Status s ON h.StatusID = s.StatusID
-- WHERE h.Timestamp = (SELECT MAX(Timestamp) FROM TaskStatusHistory WHERE TaskID = t.TaskID);
-- 
-- SELECT t.TaskID, t.TaskName, s.StatusName, h.Timestamp
-- FROM Tasks t
-- JOIN TaskStatusHistory h ON t.TaskID = h.TaskID
-- JOIN Status s ON h.StatusID = s.StatusID
-- ORDER BY t.TaskID, h.Timestamp;
-- 
-- SELECT t.TaskID, t.TaskName, p.ProjectName, u.Fullname, t.StartDate, t.EndDate, s.StatusName
-- FROM Tasks t
-- JOIN Projects p ON t.ProjectID = p.ProjectID
-- JOIN Users u ON t.ExecutorID = u.UserID
-- JOIN Status s ON t.StatusID = s.StatusID
-- ORDER BY t.TaskID;
-- 
-- SELECT t.TaskID, t.TaskName, p.ProjectName, u.Fullname, t.StartDate, t.EndDate, s.StatusName
-- FROM Tasks t
-- JOIN Projects p ON t.ProjectID = p.ProjectID
-- JOIN Users u ON t.ExecutorID = u.UserID
-- JOIN TaskStatusHistory h ON t.TaskID = h.TaskID
-- JOIN Status s ON h.StatusID = s.StatusID
-- WHERE h.Timestamp = (SELECT MAX(Timestamp) FROM TaskStatusHistory WHERE TaskID = t.TaskID)
-- ORDER BY t.TaskID;
-- 
-- 
-- SELECT TaskID, StatusID
-- FROM TaskStatusHistory
-- WHERE Timestamp = (SELECT MAX(Timestamp) FROM TaskStatusHistory WHERE TaskID = 1);
-- 
-- SELECT TaskID, StatusID, Timestamp
-- FROM TaskStatusHistory
-- WHERE TaskID = 1
-- ORDER BY Timestamp;
-- 
-- SELECT t.TaskID, t.TaskName, s.StatusName, h.Timestamp
-- FROM Tasks t
-- JOIN TaskStatusHistory h ON t.TaskID = h.TaskID
-- JOIN Status s ON h.StatusID = s.StatusID
-- ORDER BY t.TaskID, h.Timestamp;

-- Thêm task, hiện task
SELECT 
    T.TaskID,
    T.TaskName,
    P.ProjectName,
    U.Fullname AS ExecutorName,
    T.StartDate,
    T.EndDate,
    S.StatusName
FROM Tasks T
JOIN Projects P ON T.ProjectID = P.ProjectID
JOIN Users U ON T.ExecutorID = U.UserID
JOIN TaskStatusHistory H ON T.TaskID = H.TaskID
JOIN Status S ON H.StatusID = S.StatusID
ORDER BY T.TaskID;

SELECT * FROM Status;

INSERT INTO Tasks(TaskName, StartDate, EndDate, ExecutorID, ProjectID, StatusID) VALUES(?, ?, ?, ?, ?, ?);
INSERT INTO TaskStatusHistory(TaskID, StatusID) VALUES(?, ?);
-- Thay đổi định dạng DATE 
ALTER TABLE Tasks MODIFY COLUMN StartDate TIMESTAMP;
ALTER TABLE Tasks MODIFY COLUMN EndDate TIMESTAMP;

-- Cập nhật task
UPDATE Tasks SET 
TaskName = "Làm web bán quần áo", 
StartDate = "2023-06-01 10:00:15", 
EndDate = "2023-07-05 14:13:00", 
ExecutorID = 6, 
ProjectID = 9, 
StatusID = 3 WHERE TaskID = 2;
-- Thêm một bản ghi mới vào bảng TaskStatusHistory
INSERT INTO TaskStatusHistory(TaskID, StatusID)
VALUES(2, 3); 

SELECT Tasks.*, Users.Fullname , Projects.ProjectName , Status.StatusName 
FROM Tasks
JOIN Users ON Tasks.ExecutorID = Users.UserID 
JOIN Projects ON Tasks.ProjectID = Projects.ProjectID 
JOIN Status ON Tasks.StatusID  = Status.StatusID 
WHERE Tasks.TaskID = 2;

-- Query load danh sách dữ liệu
SELECT Projects.ProjectID ,Projects.ProjectName FROM Projects;
SELECT Users.UserID ,Users.Fullname FROM Users;
SELECT Status.StatusID ,Status.StatusName FROM Status;

DELETE FROM TaskStatusHistory WHERE TaskID = 6;
DELETE FROM Tasks WHERE TaskID = 6;
