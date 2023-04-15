#Industry
CREATE TABLE Industry (
	Id bigint NOT NULL AUTO_INCREMENT,
	Description varchar(20) NOT NULL,
	PRIMARY KEY (Id)
);
INSERT INTO Industry(Description) VALUES ('Education'), ('Software Development'), ('Consultation'), ('Finance');

#Skill
CREATE TABLE Skill (
	Id bigint NOT NULL AUTO_INCREMENT,
	Description varchar(20) NOT NULL,
	PRIMARY KEY (Id)
);
INSERT INTO Skill(Description) VALUES ('Communication'), ('BackEnd'), ('Cloud'), ('Web Dev');

#UserType
CREATE TABLE UserType(
	Id int NOT NULL AUTO_INCREMENT,
	Description varchar(20) NOT NULL,
	PRIMARY KEY (Id)
);
INSERT INTO UserType(Description) VALUES ('Admin'), ('Student'), ('Instructor'), ('Company');

#JobLevel
CREATE TABLE JobLevel(
	Id int NOT NULL AUTO_INCREMENT,
	Description varchar(20) NOT NULL,
	PRIMARY KEY (Id)
);
INSERT INTO JobLevel(Description) VALUES ('Junior'), ('Mid'), ('Senior');

#RecommendationStatus
CREATE TABLE RecommendationStatus(
	Id int NOT NULL AUTO_INCREMENT,
	Description varchar(20) NOT NULL,
	PRIMARY KEY (Id)
);
INSERT INTO RecommendationStatus(Description) VALUES ('Pending'), ('Approved'), ('Rejected');

#User
CREATE TABLE User (
	Id bigint NOT NULL AUTO_INCREMENT,
	Email varchar(50) NOT NULL,
	Password varchar(20) NOT NULL,
	Name varchar(30) NOT NULL,
	Headline varchar(20) NULL,
	TypeId int NOT NULL,
	IndustryId bigint NULL,
	About varchar(1500) NULL,
	Location varchar(20) NULL,
	CreatedOn datetime(6),
	PRIMARY KEY (Id),
	CONSTRAINT FK_User_Industry FOREIGN KEY(IndustryId) REFERENCES Industry(Id),
	CONSTRAINT FK_User_Type FOREIGN KEY(TypeId) REFERENCES UserType(Id)
);

#Follower
CREATE TABLE Follower (
	Id bigint NOT NULL AUTO_INCREMENT,
	UserId bigint NOT NULL,
	FollowingId bigint NOT NULL,
	FollowedOn datetime(6) NOT NULL,
	PRIMARY KEY (Id),
	CONSTRAINT FK_Follower_User FOREIGN KEY (UserId) REFERENCES User(Id),
	CONSTRAINT FK_Follower_Following FOREIGN KEY (FollowingId) REFERENCES User(Id)
);

#Post
CREATE TABLE Post (
	Id bigint NOT NULL AUTO_INCREMENT,
	UserId bigint NOT NULL,
	Content varchar(500) NOT NULL,
	PostedOn datetime(6) NOT NULL,
	PRIMARY KEY (Id),
	CONSTRAINT FK_Post_User FOREIGN KEY (UserId) REFERENCES User(Id)
);

#Comment
CREATE TABLE Comment (
	Id bigint NOT NULL AUTO_INCREMENT,
	UserId bigint NOT NULL,
	PostId bigint NOT NULL,
	Content varchar(500) NOT NULL,
	CommentedOn DATETIME(6),
	CONSTRAINT PK_Comment PRIMARY KEY (Id),
	CONSTRAINT FK_Comment_User FOREIGN KEY (UserId) REFERENCES User(Id),
	CONSTRAINT FK_Comment_Post FOREIGN KEY (PostId) REFERENCES Post(Id)
);

#Job
CREATE TABLE Job (
	Id bigint NOT NULL AUTO_INCREMENT,
	CompanyId bigint NOT NULL,
	Title varchar(20) NOT NULL,
	LevelId INT NOT NULL,
	Description varchar(20) NOT NULL,
	IsActive bit NOT NULL,
	PostedOn datetime(6) NOT NULL,
	CONSTRAINT PK_Job PRIMARY KEY (Id),
	CONSTRAINT FK_Job_Company FOREIGN KEY (CompanyId) REFERENCES User(Id),
	CONSTRAINT FK_Job_Level FOREIGN KEY (LevelId) REFERENCES JobLevel(Id)
);

#JobSkill
CREATE TABLE JobSkill(
	Id bigint NOT NULL AUTO_INCREMENT,
	JobId bigint NOT NULL,
	SkillId bigint NOT NULL,
	CONSTRAINT PK_JobSkill PRIMARY KEY (Id),
	CONSTRAINT FK_JobSkill_Job FOREIGN KEY (JobId) REFERENCES Job(Id),
	CONSTRAINT FK_JobSkill_Skill FOREIGN KEY (SkillId) REFERENCES Skill(Id)
);

#UserSkill
CREATE TABLE UserSkill(
	Id bigint NOT NULL AUTO_INCREMENT,
	UserId bigint NOT NULL,
	SkillId bigint NOT NULL,
	CONSTRAINT PK_UserSkill PRIMARY KEY (Id),
	CONSTRAINT FK_UserSkill_User FOREIGN KEY (UserId) REFERENCES User(Id),
	CONSTRAINT FK_UserSkill_Skill FOREIGN KEY (SkillId) REFERENCES Skill(Id)
);

#JobApplication
CREATE TABLE JobApplication (
	Id bigint NOT NULL AUTO_INCREMENT,
	JobId bigint NOT NULL,
	StudentId bigint NOT NULL,
	CONSTRAINT PK_JobApplication PRIMARY KEY (Id),
	CONSTRAINT FK_JobApplication_Job FOREIGN KEY (JobId) REFERENCES Job(Id),
	CONSTRAINT FK_JobApplication_Student FOREIGN KEY (StudentId) REFERENCES User(Id)
);

#Recommendation
CREATE TABLE Recommendation(
	Id bigint NOT NULL AUTO_INCREMENT,
	StudentId bigint NOT NULL,
	InstructorId bigint NOT NULL,
	Request varchar(500) NOT NULL,
	Content varchar(500) NOT NULL,
	StatusId int NOT NULL,
	CONSTRAINT PK_Recommendation PRIMARY KEY (Id),
	CONSTRAINT FK_Recommendation_Student FOREIGN KEY (StudentId) REFERENCES User(Id),
	CONSTRAINT FK_Recommendation_Instructor FOREIGN KEY (InstructorId) REFERENCES User(Id),
	CONSTRAINT FK_Recommendation_Status FOREIGN KEY (StatusId) REFERENCES RecommendationStatus(Id)
);