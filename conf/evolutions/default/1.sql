# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table image (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  data                      blob,
  constraint pk_image primary key (id))
;


create table category_image (
  category_id                    bigint not null,
  image_id                       bigint not null,
  constraint pk_category_image primary key (category_id, image_id))
;
create sequence category_seq;

create sequence image_seq;




alter table category_image add constraint fk_category_image_category_01 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table category_image add constraint fk_category_image_image_02 foreign key (image_id) references image (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists category_image;

drop table if exists image;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists image_seq;

