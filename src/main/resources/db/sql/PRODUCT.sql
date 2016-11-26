DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE PRODUCT (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY, 
	DESCRIPTION VARCHAR(255), 
	NAME VARCHAR(255) NOT NULL, 
	PRICE DECIMAL(19,2) NOT NULL, 
	PRIMARY KEY(ID)
);