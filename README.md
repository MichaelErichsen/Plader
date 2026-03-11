 Record collection application

This is an application maintaining a record collection.
All text is in Danish

## This is a Java SWT program

Data resides in a PostgreSQL database

CREATE TABLE public.plade (
	forlag varchar(255) NOT NULL,
	nummer varchar(255) NOT NULL,
	kunstner varchar(255) NULL,
	titel varchar(255) NULL,
	volume int4 DEFAULT 0 NULL,
	medium varchar(255) NOT NULL,
	antal int4 DEFAULT 1 NULL,
	aar int4 NULL,
	oprettet varchar(255) NULL,
	klassisk varchar(3) NOT NULL,
	CONSTRAINT plade_unique UNIQUE (nummer, forlag)
);