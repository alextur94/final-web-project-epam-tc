CREATE DATABASE rent_cars
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE public.account
(
    id serial,
    role_id smallint NOT NULL,
    name character varying(45),
    surname character varying(45),
    email character varying(100),
    phone character varying(45),
    document_id character varying(45),
    address character varying(255),
    drive_license_number character varying(45),
    balance numeric(7, 2),
    status smallint,
    PRIMARY KEY (id),
    CONSTRAINT email UNIQUE (email)
        INCLUDE(email)
);

ALTER TABLE IF EXISTS public.account
    OWNER to postgres;
	
CREATE TABLE public."user"
(
    id serial,
    login character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    account_id integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT login_unique UNIQUE (login),
    CONSTRAINT account_id_unique UNIQUE (account_id),
    CONSTRAINT fk_account_user FOREIGN KEY (account_id)
        REFERENCES public.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public."user"
    OWNER to postgres;
	
CREATE TABLE public.price
(
    id serial,
    price_per_day numeric(7, 2),
    price_per_hour numeric(7, 2),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.price
    OWNER to postgres;
	
CREATE TABLE public.car
(
    id serial,
    brand character varying(45) NOT NULL,
    model character varying(45) NOT NULL,
    year smallint NOT NULL,
    level smallint NOT NULL,
    body smallint NOT NULL,
    engine_volume smallint NOT NULL,
    transmission smallint NOT NULL,
    doors smallint NOT NULL,
    color character varying(45) NOT NULL,
    available smallint NOT NULL,
    price_id integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_car_price FOREIGN KEY (price_id)
        REFERENCES public.price (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.car
    OWNER to postgres;
	
CREATE TABLE public.mark
(
    id serial,
    description text,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.mark
    OWNER to postgres;
	
CREATE TABLE public.insurance
(
    id serial,
    type smallint NOT NULL,
    "number" character varying(45) NOT NULL,
    company character varying(45) NOT NULL,
    amount numeric(7, 2) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.insurance
    OWNER to postgres;
	
CREATE TABLE public.order_tab
(
    id serial,
    status smallint,
    rental_time integer,
    payment_status smallint,
    rent_start_dtm bigint,
    rent_end_dtm bigint,
    start_level integer,
    end_level integer,
    refusal character varying(255),
    pledge numeric(7, 2),
    current_sum numeric(7, 2),
    status_mark smallint,
    user_id integer NOT NULL,
    car_id integer NOT NULL,
    mark_id integer NOT NULL,
    insurance_id integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_insurance FOREIGN KEY (insurance_id)
        REFERENCES public.insurance (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_order_mark FOREIGN KEY (mark_id)
        REFERENCES public.mark (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_order_car FOREIGN KEY (car_id)
        REFERENCES public.car (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.order_tab
    OWNER to postgres;