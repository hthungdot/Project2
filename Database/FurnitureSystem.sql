create database FurnitureSystem

use FurnitureSystem

create table [Admin] (
Username nvarchar(50) not null,
[Password] nvarchar(50) not null,
Email nvarchar(50) null
)

insert into [Admin] values('project','123456','projectgroup3@gmail.com')

CREATE FUNCTION AUTO_IDEM()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(StaffID) FROM Staffs) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(StaffID, 3)) FROM Staffs
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'EM00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'EM0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END

create table Staffs(
StaffID varchar(10) CONSTRAINT IDEM DEFAULT DBO.AUTO_IDEM() not null primary key,
StaffName nvarchar(50) not null, 
Gender nvarchar(10) not null,
BirthDate date null,
HireDate date not null,
[Address] nvarchar(100) null,
Phone varchar(13) not null,
Username nvarchar(30) not null,
[Password] nvarchar(30) not null,
Email nvarchar(50) not null,
[Status] nvarchar(10) not null
)

insert into Staffs(StaffName,Gender,BirthDate,HireDate,[Address],Phone,Username,[Password],Email,[Status]) values('Marcus','Male','8/21/1999','8/22/2020','Paris','0955584565','marcus','marcus','marcusfromfrance@gmail.com','Enable')
insert into Staffs(StaffName,Gender,BirthDate,HireDate,[Address],Phone,Username,[Password],Email,[Status]) values('Peter','Male','1/2/1997','8/12/2020','Paris','0014147411','peter','peter','peterfromfrance@gmail.com','Enable')
insert into Staffs(StaffName,Gender,BirthDate,HireDate,[Address],Phone,Username,[Password],Email,[Status]) values('Lois','Female','3/3/2000','8/22/2020','HaNoi','0984496155','lois','lois','loisfromVietNam@gmail.com','Enable')
insert into Staffs(StaffName,Gender,BirthDate,HireDate,[Address],Phone,Username,[Password],Email,[Status]) values('Brain','Nale','1/1/2002','3/12/2020','LonDon','0558889994','brain','brain','brainfromUK@gmail.com','Enable')
insert into Staffs(StaffName,Gender,BirthDate,HireDate,[Address],Phone,Username,[Password],Email,[Status]) values('Jack','Male','8/1/1999','8/22/2021','Paris','0951234565','jack','jack','jackfromfrance@gmail.com','Enable')

CREATE FUNCTION AUTO_IDSU()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(SupplierID) FROM Suppliers) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(SupplierID, 3)) FROM Suppliers
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'SU00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'SU0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END


create table Suppliers(
SupplierID varchar(10) primary key CONSTRAINT IDSU DEFAULT DBO.AUTO_IDSU() not null,
CompanyName nvarchar(50) not null,
[Address] nvarchar(100) not null,
Phone varchar(13) not null,
Email nvarchar(50) not null,
[Status] nvarchar(10) not null
)

insert into Suppliers(CompanyName,[Address],Phone,Email,[Status]) values('ChiLai','TP.HCM','0114144444','chilaine@gmail.com','Enable');
insert into Suppliers(CompanyName,[Address],Phone,Email,[Status]) values('PVD Decor','HaNoi','0225544444','pvddecorne@gmail.com','Enable');
insert into Suppliers(CompanyName,[Address],Phone,Email,[Status]) values('ASMCS','LonDon','0244451514','asmcsne@gmail.com','Enable');
insert into Suppliers(CompanyName,[Address],Phone,Email,[Status]) values('Roircor','USA','0145654872','roircorne@gmail.com','Enable');
insert into Suppliers(CompanyName,[Address],Phone,Email,[Status]) values('Percosde','France','0225156444','percosdene@gmail.com','Enable');

CREATE FUNCTION AUTO_IDCA()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(CategoryID) FROM Categories) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(CategoryID, 3)) FROM Categories
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'CA00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'CA0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END

create table Categories(
CategoryID varchar(10) primary key CONSTRAINT IDCA DEFAULT DBO.AUTO_IDCA() not null,
CategoryName nvarchar(50) not null,
[Description] nvarchar(100) not null
)

insert into Categories (CategoryName,[Description]) values('Family','Housewares')
insert into Categories (CategoryName,[Description]) values('Office','Requisite')
insert into Categories (CategoryName,[Description]) values('School','School Things')
insert into Categories (CategoryName,[Description]) values('Kid','Kids Love It')
insert into Categories (CategoryName,[Description]) values('Hospital','so many, so much')






CREATE FUNCTION AUTO_IDPR()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(ProductID) FROM Products) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(ProductID, 3)) FROM Products
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'PR00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'PR0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END


create table Products(
ProductID varchar(10) primary key CONSTRAINT IDPR DEFAULT DBO.AUTO_IDPR(),
ProductName nvarchar(50) not null,
SupplierID varchar(10) not null REFERENCES Suppliers(SupplierID) ,
CategoryID varchar(10)  not null references Categories(CategoryID),
UnitPrice money not null,
SellPrice money not null,
QuantityInStock int null,
[Status] nvarchar(10) not null
)


insert into Products(ProductName,SupplierID,CategoryID,UnitPrice,SellPrice,QuantityInStock,[Status]) values('Desk','SU001','CA001','170',170*1.1,'21','Enable')
insert into Products(ProductName,SupplierID,CategoryID,UnitPrice,SellPrice,QuantityInStock,[Status]) values('Chair','SU002','CA004','70', 70*1.1,'21','Enable')
insert into Products(ProductName,SupplierID,CategoryID,UnitPrice,SellPrice,QuantityInStock,[Status]) values('CupBoard','SU003','CA003','70',70*1.1,'11','Enable')
insert into Products(ProductName,SupplierID,CategoryID,UnitPrice,SellPrice,QuantityInStock,[Status]) values('Desk','SU004','CA005','340',340 * 1.15,'25','Enable')
insert into Products(ProductName,SupplierID,CategoryID,UnitPrice,SellPrice,QuantityInStock,[Status]) values('Table','SU005','CA004','70',70 * 1.1,'11','Enable')


CREATE FUNCTION AUTO_IDCU()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(CustomerID) FROM Customers) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(CustomerID, 3)) FROM Customers
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'CU00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'CU0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END

create table Customers(
CustomerID varchar(10) CONSTRAINT IDCU DEFAULT DBO.AUTO_IDCU() not null primary key,
CustomerName nvarchar(50) not null,
Gender nvarchar(10) not null,
BirthDate date  null,
[Address] nvarchar(100) not null,
Phone varchar(13) not null,
[Status] nvarchar(10) not null
)

insert into Customers(CustomerName,Gender,BirthDate,[Address],Phone,[Status]) values('Linux','Male','12/12/1980','Soc Trang','0115846521','Enable')
insert into Customers(CustomerName,Gender,BirthDate,[Address],Phone,[Status]) values('Linda','Female','1/1/1999','Ninh Kieu, Tp Can Tho','0241552521','Enable')
insert into Customers(CustomerName,Gender,BirthDate,[Address],Phone,[Status]) values('Tony','Male','2/2/2000','Ninh Kieu, Tp Can Tho','0145154758', 'Enable')
insert into Customers(CustomerName,Gender,BirthDate,[Address],Phone,[Status]) values('Lyli','Female','1/12/1980','Soc Trang','0665650125','Enable')
insert into Customers(CustomerName,Gender,BirthDate,[Address],Phone,[Status]) values('TrucAnh','Female','9/17/1990','Soc Trang','0388884444','Enable')
insert into Customers(CustomerName,Gender,BirthDate,[Address],Phone,[Status]) values('HoangHao','Male','5/4/1990','Soc Trang','0665650125','Enable')

CREATE FUNCTION AUTO_IDPM()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(PromotionID) FROM Promotion) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(PromotionID, 3)) FROM Promotion
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'PM00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'PM0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END

create table Promotion
(
PromotionID varchar(10) CONSTRAINT IDPM DEFAULT DBO.AUTO_IDPM() not null primary key,
PromotionName nvarchar(50) not null,
PromotionDate date not null,
Discount float not null,
AmountOfMoney int not null,
Quantity int not null,
Note nvarchar(100) ,
)

insert into Promotion(PromotionName,PromotionDate,Discount,AmountOfMoney,Quantity,Note) values('sale2/08','8/23/2021','0.1','100','20','Big Promotion')
insert into Promotion(PromotionName,PromotionDate,Discount,AmountOfMoney,Quantity,Note) values('sale 6/6','6/6/2021','0.15','200','0','Big Promotion')
insert into Promotion(PromotionName,PromotionDate,Discount,AmountOfMoney,Quantity,Note) values('sale 7/7','7/7/2021','0.2','500','0','Big Sale')
insert into Promotion(PromotionName,PromotionDate,Discount,AmountOfMoney,Quantity,Note) values('sale 8/3','3/8/2021','0.1','200','0','Happy 8/3')
insert into Promotion(PromotionName,PromotionDate,Discount,AmountOfMoney,Quantity,Note) values('sale 8/8','8/8/2021','0.15','300','0','Super hot discount')
insert into Promotion(PromotionName,PromotionDate,Discount,AmountOfMoney,Quantity,Note) values('No','8/8/2020','0','0','0','No discount')

CREATE FUNCTION AUTO_IDSH()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(ShipperID) FROM Shippers) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(ShipperID, 3)) FROM Shippers
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'SH00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'SH0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END

create table Shippers
(
ShipperID varchar(10) CONSTRAINT IDSH DEFAULT DBO.AUTO_IDSH() not null primary key,
CompanyName nvarchar(50) not null,
Phone varchar(13) not null,
Status nvarchar(10) not null
)

insert into Shippers(CompanyName,Phone,Status) values ('ShipFlash','0147896632','Enable')
insert into Shippers(CompanyName,Phone,Status) values ('ShipSlow','0145632258','Disable')
insert into Shippers(CompanyName,Phone,Status) values ('ShipSuper','0145796425','Enable')
insert into Shippers(CompanyName,Phone,Status) values ('ShipRacing','0943157691','Disable')
insert into Shippers(CompanyName,Phone,Status) values ('Ship','03458547964','Enable')




CREATE FUNCTION AUTO_IDOR()
RETURNS VARCHAR(5)
AS
BEGIN
	DECLARE @ID VARCHAR(5)
	IF (SELECT COUNT(OrderID) FROM Orders) = 0
		SET @ID = '0'
	ELSE
		SELECT @ID = MAX(RIGHT(OrderID, 3)) FROM Orders
		SELECT @ID = CASE
			WHEN @ID >= 0 and @ID < 9 THEN 'OR00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
			WHEN @ID >= 9 THEN 'OR0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)
		END
	RETURN @ID
END

create table Orders(
OrderID varchar(10) primary key CONSTRAINT IDOR DEFAULT DBO.AUTO_IDOR() not null,
CustomerID varchar(10) not null references Customers(CustomerID),
StaffID varchar(10) not null references Staffs(StaffID),
PromotionID varchar(10) references Promotion(PromotionID),
ShipperID varchar(10) references Shippers(ShipperID) not null,
OrderDate datetime not null,
TotalAmount real not null,
PaymentMethod nvarchar(30) not null,
Status nvarchar(10) not null
)

insert into Orders(CustomerID,StaffID,PromotionID,ShipperID,OrderDate,TotalAmount,PaymentMethod,Status) values('CU002','EM001','PM001','SH001', '8/23/2021 09:35:45','374','ATM','Enable')
insert into Orders(CustomerID,StaffID,PromotionID,ShipperID,OrderDate,TotalAmount,PaymentMethod,Status) values('CU003','EM005','PM006','SH001','8/19/2021 13:14:12','385','Money','Enable')
insert into Orders(CustomerID,StaffID,PromotionID,ShipperID,OrderDate,TotalAmount,PaymentMethod,Status) values('CU005','EM003','PM006','SH003', '7/7/2021 21:15:45','77','Money','Enable')
insert into Orders(CustomerID,StaffID,PromotionID,ShipperID,OrderDate,TotalAmount,PaymentMethod,Status) values('CU003','EM005','PM001','SH001','8/23/2021 12:21:52','391','Money','Enable')
insert into Orders(CustomerID,StaffID,PromotionID,ShipperID,OrderDate,TotalAmount,PaymentMethod,Status) values('CU003','EM005','PM006','SH005','8/22/2021 15:26:44','154','Money','Enable')

create table [Order Details](
OrderID varchar(10) not null references Orders(OrderID),
ProductID varchar(10) not null references Products(ProductID),
UnitPrice money  not null,
Quantity int not null,
PRIMARY KEY (OrderID, ProductID)
)
insert into [Order Details] values('OR001','PR001',170 * 1.1,'2');
insert into [Order Details] values('OR002','PR002',70 * 1.1,'5');
insert into [Order Details] values('OR003','PR005',70 * 1.1,'1');
insert into [Order Details] values('OR004','PR004',340 * 1.15,'1');
insert into [Order Details] values('OR005','PR005',70 * 1.1,'2');

create proc [dbo].[Employeeslogin]
	   @usename nvarchar(50),
	   @password nvarchar(50)
	   as
	   select count(*) as result
	   from [dbo].[Staffs]
	   where
	          [Username] = @usename
		  and [Password] = @password
GO

create proc [dbo].[Adminlogin]
	   @usename nvarchar(50),
	   @password nvarchar(50)
	   as
	   select count(*) as result
	   from [dbo].[Admin]
	   where
	          [Username] = @usename
		  and [Password] = @password
GO
