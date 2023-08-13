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
    RoleID int,
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
