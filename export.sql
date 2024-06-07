--------------------------------------------------------
--  File created - Friday-June-07-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence SQ_FILES
--------------------------------------------------------

   CREATE SEQUENCE  "ADMINFILEMNG"."SQ_FILES"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence SQ_FOLDERS
--------------------------------------------------------

   CREATE SEQUENCE  "ADMINFILEMNG"."SQ_FOLDERS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 365 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence SQ_FULLNAME
--------------------------------------------------------

   CREATE SEQUENCE  "ADMINFILEMNG"."SQ_FULLNAME"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Table FILES
--------------------------------------------------------

  CREATE TABLE "ADMINFILEMNG"."FILES" 
   (	"FILE_ID" VARCHAR2(8 BYTE), 
	"FILE_NAME" VARCHAR2(50 BYTE), 
	"FILE_SIZE" NUMBER, 
	"FILE_TYPE" VARCHAR2(10 BYTE), 
	"UPLOAD_DATE" DATE, 
	"PARENT_FOLDER" NUMBER, 
	"CLASS_ICON" VARCHAR2(30 BYTE) DEFAULT 'far fa-file-alt', 
	"FILE_PATH" VARCHAR2(150 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table FILE_TYPES
--------------------------------------------------------

  CREATE TABLE "ADMINFILEMNG"."FILE_TYPES" 
   (	"TYPE_ID" VARCHAR2(10 BYTE), 
	"TYPE_NAME" VARCHAR2(20 BYTE), 
	"CLASS_ICON" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table FOLDERS
--------------------------------------------------------

  CREATE TABLE "ADMINFILEMNG"."FOLDERS" 
   (	"FOLDER_ID" NUMBER, 
	"FORLDER_NAME" VARCHAR2(50 BYTE), 
	"PARENT_FOLDER" NUMBER, 
	"USERNAME" VARCHAR2(128 BYTE), 
	"FOLDER_PATH" VARCHAR2(150 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE "ADMINFILEMNG"."USERS" 
   (	"USERNAME" VARCHAR2(128 BYTE), 
	"FULLNAME" VARCHAR2(50 BYTE), 
	"PHONE" VARCHAR2(11 BYTE), 
	"EMAIL" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for View V_FILES
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "ADMINFILEMNG"."V_FILES" ("FILE_ID", "FILE_NAME", "FILE_SIZE", "FILE_TYPE", "UPLOAD_DATE", "PARENT_FOLDER", "CLASS_ICON", "FILE_PATH") AS 
  SELECT "FILE_ID","FILE_NAME","FILE_SIZE","FILE_TYPE","UPLOAD_DATE","PARENT_FOLDER","CLASS_ICON","FILE_PATH" FROM FILES
;
--------------------------------------------------------
--  DDL for View V_FILE_TYPES
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "ADMINFILEMNG"."V_FILE_TYPES" ("TYPE_ID", "TYPE_NAME", "CLASS_ICON") AS 
  SELECT TYPE_ID, RPAD(LPAD(TYPE_NAME, 20, '*'), 40, '*') AS TYPE_NAME, CLASS_ICON FROM FILE_TYPES
;
--------------------------------------------------------
--  DDL for View V_FOLDERS
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "ADMINFILEMNG"."V_FOLDERS" ("FOLDER_ID", "FORLDER_NAME", "PARENT_FOLDER", "USERNAME", "FOLDER_PATH") AS 
  SELECT "FOLDER_ID","FORLDER_NAME","PARENT_FOLDER","USERNAME","FOLDER_PATH" FROM FOLDERS
;
--------------------------------------------------------
--  DDL for View V_USERS
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "ADMINFILEMNG"."V_USERS" ("USERNAME", "FULLNAME", "PHONE", "EMAIL") AS 
  SELECT "USERNAME","FULLNAME","PHONE","EMAIL" FROM USERS
;
REM INSERTING into ADMINFILEMNG.FILES
SET DEFINE OFF;
REM INSERTING into ADMINFILEMNG.FILE_TYPES
SET DEFINE OFF;
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('html','HTML Filee','far fa-file-code');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('css','Style HTML File','far fa-file-code');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('docx','Word Document','far fa-file-word');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('pptx','Presentation File','far fa-file-powerpoint');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('xls','Spreadsheet File','far fa-file-excel');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('mp4','MP4 File','far fa-file-video');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('mp3','Mp3 File','far fa-file-audio');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('rar','Compressed file','far fa-file-alt');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('sql','SQL File','far fa-file-alt');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('mdl','Diagram File','far fa-file-alt');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('png','Image File','far fa-file-image');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('jpg','Image File','far fa-file-image');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('xlsx','Excel File','far fa-file-excel');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('zip','Zip File','far fa-file-alt');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('example','example','far fa-file-alt');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('java','Java File','far fa-file-alt');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('pdf','Tài li?u','far fa-file-pdf');
Insert into ADMINFILEMNG.FILE_TYPES (TYPE_ID,TYPE_NAME,CLASS_ICON) values ('txt','Tài li?u','far fa-file-alt');
REM INSERTING into ADMINFILEMNG.FOLDERS
SET DEFINE OFF;
Insert into ADMINFILEMNG.FOLDERS (FOLDER_ID,FORLDER_NAME,PARENT_FOLDER,USERNAME,FOLDER_PATH) values (241,'newuser',null,'newuser','data');
Insert into ADMINFILEMNG.FOLDERS (FOLDER_ID,FORLDER_NAME,PARENT_FOLDER,USERNAME,FOLDER_PATH) values (242,'newuser-(1)',null,'newuser','data');
REM INSERTING into ADMINFILEMNG.USERS
SET DEFINE OFF;
Insert into ADMINFILEMNG.USERS (USERNAME,FULLNAME,PHONE,EMAIL) values ('newuser','Lê Nguy?n Công Hoannnbb','0369656502','lhoan2822@gmail.com');
--------------------------------------------------------
--  DDL for Trigger TRG_AFTER_INSERT_USER
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ADMINFILEMNG"."TRG_AFTER_INSERT_USER" 
AFTER INSERT ON USERS
FOR EACH ROW
BEGIN
    INSERT INTO adminfilemng.folders (FOLDER_ID, FORLDER_NAME, USERNAME, FOLDER_PATH)
    VALUES (adminfilemng.SQ_FOLDERS.NEXTVAL, :NEW.USERNAME, :NEW.USERNAME, 'data');
END;

/
ALTER TRIGGER "ADMINFILEMNG"."TRG_AFTER_INSERT_USER" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRG_BEFORE_INSERT_FOLDER
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ADMINFILEMNG"."TRG_BEFORE_INSERT_FOLDER" 
BEFORE INSERT ON FOLDERS
FOR EACH ROW
DECLARE
    v_count NUMBER;
    v_new_folder_name VARCHAR2(50);
    v_counter NUMBER := 1;
BEGIN
    v_new_folder_name := :NEW.FORLDER_NAME;

    -- Ki?m tra xem FOLDER_NAME và FOLDER_PATH ?ã t?n t?i ch?a
    LOOP
        SELECT COUNT(*)
        INTO v_count
        FROM FOLDERS
        WHERE FORLDER_NAME = v_new_folder_name
          AND FOLDER_PATH = :NEW.FOLDER_PATH;

        IF v_count = 0 THEN
            EXIT; -- N?u không t?n t?i, thoát kh?i vòng l?p
        ELSE
            -- N?u t?n t?i, thêm s? th? t? vào tên folder
            v_new_folder_name := :NEW.FORLDER_NAME || '-(' || v_counter || ')';
            v_counter := v_counter + 1;
        END IF;
    END LOOP;

    -- Gán giá tr? m?i cho :NEW.FORLDER_NAME
    :NEW.FORLDER_NAME := v_new_folder_name;
END;

/
ALTER TRIGGER "ADMINFILEMNG"."TRG_BEFORE_INSERT_FOLDER" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRG_BEFORE_INSERT_USER
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ADMINFILEMNG"."TRG_BEFORE_INSERT_USER" 
BEFORE INSERT ON USERS
FOR EACH ROW
DECLARE
    v_count NUMBER;
    v_new_fullname VARCHAR2(50);
BEGIN
    v_new_fullname := :NEW.FULLNAME;

    -- Ki?m tra xem FULLNAME ?ã t?n t?i ch?a
    SELECT COUNT(*)
    INTO v_count
    FROM USERS
    WHERE FULLNAME = v_new_fullname;

    IF v_count > 0 THEN
        -- N?u FULLNAME ?ã t?n t?i, thêm h?u t? (s? th? t?)
        SELECT COUNT(*)
        INTO v_count
        FROM USERS
        WHERE FULLNAME LIKE v_new_fullname || ' (%)';

        -- T?o FULLNAME m?i v?i s? th? t?
        v_new_fullname := v_new_fullname || ' (' || (v_count + 1) || ')';
    END IF;

    -- Gán giá tr? m?i cho :NEW.FULLNAME
    :NEW.FULLNAME := v_new_fullname;
END;

/
ALTER TRIGGER "ADMINFILEMNG"."TRG_BEFORE_INSERT_USER" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRG_UPDATE_FILES_AFTER_FILETYPE_DELETE
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ADMINFILEMNG"."TRG_UPDATE_FILES_AFTER_FILETYPE_DELETE" 
AFTER DELETE ON FILE_TYPES
FOR EACH ROW
BEGIN
    -- C?p nh?t các files có FILE_TYPE trùng v?i TYPE_ID v?a b? xóa
    UPDATE FILES
    SET FILE_TYPE = 'example'
    WHERE FILE_TYPE = :OLD.TYPE_ID;
END;
/
ALTER TRIGGER "ADMINFILEMNG"."TRG_UPDATE_FILES_AFTER_FILETYPE_DELETE" ENABLE;
--------------------------------------------------------
--  DDL for Procedure DELETE_FROM_FILES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."DELETE_FROM_FILES" (
    p_file_id IN FILES.FILE_ID%TYPE
) IS
BEGIN
    DELETE FROM adminfilemng.FILES WHERE FILE_ID = p_file_id;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END delete_from_files;

/
--------------------------------------------------------
--  DDL for Procedure DELETE_FROM_FILE_TYPES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."DELETE_FROM_FILE_TYPES" (
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE
) IS
BEGIN
    DELETE FROM adminfilemng.FILE_TYPES WHERE TYPE_ID = p_type_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END delete_from_file_types;

/
--------------------------------------------------------
--  DDL for Procedure DELETE_FROM_FOLDERS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."DELETE_FROM_FOLDERS" (
    p_folder_id IN FOLDERS.FOLDER_ID%TYPE
) IS
BEGIN
    DELETE FROM adminfilemng.FOLDERS WHERE FOLDER_ID = p_folder_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END delete_from_folders;

/
--------------------------------------------------------
--  DDL for Procedure INSERT_INTO_FILES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."INSERT_INTO_FILES" (
    p_file_id IN FILES.FILE_ID%TYPE,
    p_file_name IN FILES.FILE_NAME%TYPE,
    p_file_size IN FILES.FILE_SIZE%TYPE,
    p_file_type IN FILES.FILE_TYPE%TYPE,
    p_parent_folder IN FILES.PARENT_FOLDER%TYPE,
    p_class_icon IN FILES.CLASS_ICON%TYPE,
    p_file_path IN FILES.FILE_PATH%TYPE
) IS
BEGIN
    INSERT INTO adminfilemng.FILES (FILE_ID, FILE_NAME, FILE_SIZE, FILE_TYPE, UPLOAD_DATE, PARENT_FOLDER, CLASS_ICON, FILE_PATH) 
    VALUES (p_file_id, p_file_name, p_file_size, p_file_type, SYSDATE, p_parent_folder, p_class_icon, p_file_path);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END insert_into_files;

/
--------------------------------------------------------
--  DDL for Procedure INSERT_INTO_FILE_TYPES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."INSERT_INTO_FILE_TYPES" (
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE,
    p_type_name IN FILE_TYPES.TYPE_NAME%TYPE,
    p_class_icon IN FILE_TYPES.CLASS_ICON%TYPE
) IS
BEGIN
    INSERT INTO adminfilemng.FILE_TYPES (TYPE_ID, TYPE_NAME, CLASS_ICON) 
    VALUES (p_type_id, p_type_name, p_class_icon);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END insert_into_file_types;

/
--------------------------------------------------------
--  DDL for Procedure INSERT_INTO_FOLDERS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."INSERT_INTO_FOLDERS" (
    p_folder_name IN FOLDERS.FORLDER_NAME%TYPE,
    p_parent_folder IN FOLDERS.PARENT_FOLDER%TYPE,
    p_username IN FOLDERS.USERNAME%TYPE,
    p_folder_path IN FOLDERS.FOLDER_PATH%TYPE
) IS
BEGIN
    INSERT INTO adminfilemng.FOLDERS (FOLDER_ID, FORLDER_NAME, PARENT_FOLDER, USERNAME, FOLDER_PATH) 
    VALUES (adminfilemng.SQ_FOLDERS.NEXTVAL, p_folder_name, p_parent_folder, p_username, p_folder_path);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END insert_into_folders;

/
--------------------------------------------------------
--  DDL for Procedure RENAME_FILE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."RENAME_FILE" (
    p_file_id IN FILES.FILE_ID%TYPE,
    p_file_name IN FILES.FILE_NAME%TYPE
) IS
BEGIN
    UPDATE adminfilemng.FILES SET FILE_NAME = p_file_name WHERE FILE_ID = p_file_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END rename_file;

/
--------------------------------------------------------
--  DDL for Procedure RENAME_FOLDER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."RENAME_FOLDER" (
    p_folder_id IN FOLDERS.FOLDER_ID%TYPE,
    p_folder_name IN FOLDERS.FORLDER_NAME%TYPE
) IS
BEGIN
    UPDATE adminfilemng.FOLDERS SET FORLDER_NAME = p_folder_name WHERE FOLDER_ID = p_folder_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END rename_folder;

/
--------------------------------------------------------
--  DDL for Procedure UPDATE_FILE_TYPES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."UPDATE_FILE_TYPES" (
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE,
    p_type_name IN FILE_TYPES.TYPE_NAME%TYPE,
    p_class_icon IN FILE_TYPES.CLASS_ICON%TYPE
) IS
BEGIN
    UPDATE adminfilemng.FILE_TYPES 
    SET TYPE_NAME = p_type_name, CLASS_ICON = p_class_icon 
    WHERE TYPE_ID = p_type_id;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END update_file_types;

/
--------------------------------------------------------
--  DDL for Procedure UPDATE_USER_INFO
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADMINFILEMNG"."UPDATE_USER_INFO" (
    p_username IN USERS.USERNAME%TYPE,
    p_fullname IN USERS.FULLNAME%TYPE,
    p_email IN USERS.EMAIL%TYPE,
    p_phone IN USERS.PHONE%TYPE
) IS
BEGIN
    UPDATE adminfilemng.USERS 
    SET FULLNAME = p_fullname, EMAIL = p_email, PHONE = p_phone 
    WHERE USERNAME = p_username;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END update_user_info;

/
--------------------------------------------------------
--  DDL for Package FILE_PKG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PACKAGE "ADMINFILEMNG"."FILE_PKG" AS
  PROCEDURE insert_into_files(
    p_file_id IN FILES.FILE_ID%TYPE,
    p_file_name IN FILES.FILE_NAME%TYPE,
    p_file_size IN FILES.FILE_SIZE%TYPE,
    p_file_type IN FILES.FILE_TYPE%TYPE,
    p_parent_folder IN FILES.PARENT_FOLDER%TYPE,
    p_class_icon IN FILES.CLASS_ICON%TYPE,
    p_file_path IN FILES.FILE_PATH%TYPE
  );

  PROCEDURE delete_from_files(
    p_file_id IN FILES.FILE_ID%TYPE
  );

  PROCEDURE rename_file(
    p_file_id IN FILES.FILE_ID%TYPE,
    p_file_name IN FILES.FILE_NAME%TYPE
  );

  FUNCTION get_total_file_size(
    p_folder_path IN FILES.FILE_PATH%TYPE
  ) RETURN NUMBER;
END FILE_PKG;

/
--------------------------------------------------------
--  DDL for Package FILE_TYPE_PKG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PACKAGE "ADMINFILEMNG"."FILE_TYPE_PKG" AS
  PROCEDURE insert_into_file_types(
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE,
    p_type_name IN FILE_TYPES.TYPE_NAME%TYPE,
    p_class_icon IN FILE_TYPES.CLASS_ICON%TYPE
  );
  PROCEDURE update_file_types(
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE,
    p_type_name IN FILE_TYPES.TYPE_NAME%TYPE,
    p_class_icon IN FILE_TYPES.CLASS_ICON%TYPE
  );
  PROCEDURE delete_from_file_types(
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE
  );
END FILE_TYPE_PKG;

/
--------------------------------------------------------
--  DDL for Package FOLDER_PKG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PACKAGE "ADMINFILEMNG"."FOLDER_PKG" AS
  PROCEDURE insert_into_folders(
    p_folder_name IN FOLDERS.FORLDER_NAME%TYPE,
    p_parent_folder IN FOLDERS.PARENT_FOLDER%TYPE,
    p_username IN FOLDERS.USERNAME%TYPE,
    p_folder_path IN FOLDERS.FOLDER_PATH%TYPE
  );

  PROCEDURE delete_from_folders(
    p_folder_id IN FOLDERS.FOLDER_ID%TYPE
  );

  PROCEDURE rename_folder(
    p_folder_id IN FOLDERS.FOLDER_ID%TYPE,
    p_folder_name IN FOLDERS.FORLDER_NAME%TYPE
  );

  FUNCTION count_files_and_folders(
    p_folder_path IN V_FOLDERS.FOLDER_PATH%TYPE
  ) RETURN NUMBER;
END FOLDER_PKG;

/
--------------------------------------------------------
--  DDL for Package USER_PKG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PACKAGE "ADMINFILEMNG"."USER_PKG" AS
  PROCEDURE update_user_info(
    p_username IN USERS.USERNAME%TYPE,
    p_fullname IN USERS.FULLNAME%TYPE,
    p_email IN USERS.EMAIL%TYPE,
    p_phone IN USERS.PHONE%TYPE
  );
END USER_PKG;

/
--------------------------------------------------------
--  DDL for Package Body FILE_PKG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PACKAGE BODY "ADMINFILEMNG"."FILE_PKG" AS
  PROCEDURE insert_into_files(
    p_file_id IN FILES.FILE_ID%TYPE,
    p_file_name IN FILES.FILE_NAME%TYPE,
    p_file_size IN FILES.FILE_SIZE%TYPE,
    p_file_type IN FILES.FILE_TYPE%TYPE,
    p_parent_folder IN FILES.PARENT_FOLDER%TYPE,
    p_class_icon IN FILES.CLASS_ICON%TYPE,
    p_file_path IN FILES.FILE_PATH%TYPE
  ) IS
  BEGIN
    INSERT INTO adminfilemng.FILES (FILE_ID, FILE_NAME, FILE_SIZE, FILE_TYPE, UPLOAD_DATE, PARENT_FOLDER, CLASS_ICON, FILE_PATH) 
    VALUES (p_file_id, p_file_name, p_file_size, p_file_type, SYSDATE, p_parent_folder, p_class_icon, p_file_path);
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END insert_into_files;

  PROCEDURE delete_from_files(
    p_file_id IN FILES.FILE_ID%TYPE
  ) IS
  BEGIN
    DELETE FROM adminfilemng.FILES WHERE FILE_ID = p_file_id;
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END delete_from_files;

  PROCEDURE rename_file(
    p_file_id IN FILES.FILE_ID%TYPE,
    p_file_name IN FILES.FILE_NAME%TYPE
  ) IS
  BEGIN
    UPDATE adminfilemng.FILES SET FILE_NAME = p_file_name WHERE FILE_ID = p_file_id;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END rename_file;

  FUNCTION get_total_file_size(
    p_folder_path IN FILES.FILE_PATH%TYPE
  ) RETURN NUMBER IS
    v_total_size NUMBER;
  BEGIN
    SELECT SUM(FILE_SIZE) INTO v_total_size
    FROM adminfilemng.V_FILES 
    WHERE FILE_PATH LIKE p_folder_path || '%';

    RETURN v_total_size;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
      RETURN NULL;
  END get_total_file_size;
END FILE_PKG;

/
--------------------------------------------------------
--  DDL for Package Body FILE_TYPE_PKG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PACKAGE BODY "ADMINFILEMNG"."FILE_TYPE_PKG" AS
  PROCEDURE insert_into_file_types(
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE,
    p_type_name IN FILE_TYPES.TYPE_NAME%TYPE,
    p_class_icon IN FILE_TYPES.CLASS_ICON%TYPE
  ) IS
  BEGIN
    INSERT INTO adminfilemng.FILE_TYPES (TYPE_ID, TYPE_NAME, CLASS_ICON) 
    VALUES (p_type_id, p_type_name, p_class_icon);
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END insert_into_file_types;

  PROCEDURE update_file_types(
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE,
    p_type_name IN FILE_TYPES.TYPE_NAME%TYPE,
    p_class_icon IN FILE_TYPES.CLASS_ICON%TYPE
  ) IS
  BEGIN
    UPDATE adminfilemng.FILE_TYPES 
    SET TYPE_NAME = p_type_name, CLASS_ICON = p_class_icon 
    WHERE TYPE_ID = p_type_id;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END update_file_types;

  PROCEDURE delete_from_file_types(
    p_type_id IN FILE_TYPES.TYPE_ID%TYPE
  ) IS
  BEGIN
    DELETE FROM adminfilemng.FILE_TYPES WHERE TYPE_ID = p_type_id;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END delete_from_file_types;
END FILE_TYPE_PKG;

/
--------------------------------------------------------
--  DDL for Package Body FOLDER_PKG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PACKAGE BODY "ADMINFILEMNG"."FOLDER_PKG" AS
  PROCEDURE insert_into_folders(
    p_folder_name IN FOLDERS.FORLDER_NAME%TYPE,
    p_parent_folder IN FOLDERS.PARENT_FOLDER%TYPE,
    p_username IN FOLDERS.USERNAME%TYPE,
    p_folder_path IN FOLDERS.FOLDER_PATH%TYPE
  ) IS
  BEGIN
    INSERT INTO adminfilemng.FOLDERS (FOLDER_ID, FORLDER_NAME, PARENT_FOLDER, USERNAME, FOLDER_PATH) 
    VALUES (adminfilemng.SQ_FOLDERS.NEXTVAL, p_folder_name, p_parent_folder, p_username, p_folder_path);
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END insert_into_folders;

  PROCEDURE delete_from_folders(
    p_folder_id IN FOLDERS.FOLDER_ID%TYPE
  ) IS
  BEGIN
    DELETE FROM adminfilemng.FOLDERS WHERE FOLDER_ID = p_folder_id;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END delete_from_folders;

  PROCEDURE rename_folder(
    p_folder_id IN FOLDERS.FOLDER_ID%TYPE,
    p_folder_name IN FOLDERS.FORLDER_NAME%TYPE
  ) IS
  BEGIN
    UPDATE adminfilemng.FOLDERS SET FORLDER_NAME = p_folder_name WHERE FOLDER_ID = p_folder_id;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END rename_folder;

  FUNCTION count_files_and_folders(
    p_folder_path IN V_FOLDERS.FOLDER_PATH%TYPE
  ) RETURN NUMBER IS
    v_file_count NUMBER;
    v_folder_count NUMBER;
  BEGIN
    SELECT COUNT(*) INTO v_file_count
    FROM adminfilemng.V_FILES 
    WHERE FILE_PATH LIKE p_folder_path || '%';

    SELECT COUNT(*) INTO v_folder_count
    FROM adminfilemng.V_FOLDERS 
    WHERE FOLDER_PATH LIKE p_folder_path || '%';

    RETURN v_file_count + v_folder_count;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
      RETURN NULL;
  END count_files_and_folders;
END FOLDER_PKG;

/
--------------------------------------------------------
--  DDL for Package Body USER_PKG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PACKAGE BODY "ADMINFILEMNG"."USER_PKG" AS
  PROCEDURE update_user_info(
    p_username IN USERS.USERNAME%TYPE,
    p_fullname IN USERS.FULLNAME%TYPE,
    p_email IN USERS.EMAIL%TYPE,
    p_phone IN USERS.PHONE%TYPE
  ) IS
  BEGIN
    UPDATE adminfilemng.USERS 
    SET FULLNAME = p_fullname, EMAIL = p_email, PHONE = p_phone 
    WHERE USERNAME = p_username;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
  END update_user_info;
END USER_PKG;

/
--------------------------------------------------------
--  DDL for Function CALCULATE_USED_CAPACITY
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE FUNCTION "ADMINFILEMNG"."CALCULATE_USED_CAPACITY" (
    p_username IN USERS.USERNAME%TYPE
) RETURN NUMBER IS
    v_total_size NUMBER;
BEGIN
    SELECT SUM(FILE_SIZE) INTO v_total_size
    FROM FILES F JOIN FOLDERS FO ON F.PARENT_FOLDER = FO.FOLDER_ID
    WHERE FO.USERNAME = p_username;

    -- If no files are found, set the total size to 0
    IF v_total_size IS NULL THEN
        v_total_size := 0;
    END IF;

    RETURN v_total_size;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        RETURN NULL;
END calculate_used_capacity;

/
--------------------------------------------------------
--  DDL for Function COUNT_FILES_AND_FOLDERS
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE FUNCTION "ADMINFILEMNG"."COUNT_FILES_AND_FOLDERS" (
    p_folder_path IN V_FOLDERS.FOLDER_PATH%TYPE
) RETURN NUMBER IS
    v_file_count NUMBER;
    v_folder_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_file_count
    FROM adminfilemng.V_FILES 
    WHERE FILE_PATH LIKE p_folder_path || '%';

    SELECT COUNT(*) INTO v_folder_count
    FROM adminfilemng.V_FOLDERS 
    WHERE FOLDER_PATH LIKE p_folder_path || '%';

    RETURN v_file_count + v_folder_count;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        RETURN NULL;
END count_files_and_folders;

/
--------------------------------------------------------
--  DDL for Function GET_TOTAL_FILE_SIZE
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE FUNCTION "ADMINFILEMNG"."GET_TOTAL_FILE_SIZE" (
    p_folder_path IN FILES.FILE_PATH%TYPE
) RETURN NUMBER IS
    v_total_size NUMBER;
BEGIN
    SELECT SUM(FILE_SIZE) INTO v_total_size
    FROM adminfilemng.V_FILES 
    WHERE FILE_PATH LIKE p_folder_path || '%';

    RETURN v_total_size;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        RETURN NULL;
END get_total_file_size;

/
--------------------------------------------------------
--  Constraints for Table FOLDERS
--------------------------------------------------------

  ALTER TABLE "ADMINFILEMNG"."FOLDERS" ADD PRIMARY KEY ("FOLDER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "ADMINFILEMNG"."USERS" ADD PRIMARY KEY ("USERNAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table FILE_TYPES
--------------------------------------------------------

  ALTER TABLE "ADMINFILEMNG"."FILE_TYPES" ADD PRIMARY KEY ("TYPE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table FILES
--------------------------------------------------------

  ALTER TABLE "ADMINFILEMNG"."FILES" ADD PRIMARY KEY ("FILE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table FILES
--------------------------------------------------------

  ALTER TABLE "ADMINFILEMNG"."FILES" ADD CONSTRAINT "FK_FILES_FOLDERS" FOREIGN KEY ("PARENT_FOLDER")
	  REFERENCES "ADMINFILEMNG"."FOLDERS" ("FOLDER_ID") ENABLE;
  ALTER TABLE "ADMINFILEMNG"."FILES" ADD CONSTRAINT "FK_FILES_TYPES" FOREIGN KEY ("FILE_TYPE")
	  REFERENCES "ADMINFILEMNG"."FILE_TYPES" ("TYPE_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table FOLDERS
--------------------------------------------------------

  ALTER TABLE "ADMINFILEMNG"."FOLDERS" ADD CONSTRAINT "FK_FOLDER_FOLDERS" FOREIGN KEY ("PARENT_FOLDER")
	  REFERENCES "ADMINFILEMNG"."FOLDERS" ("FOLDER_ID") ENABLE;
  ALTER TABLE "ADMINFILEMNG"."FOLDERS" ADD CONSTRAINT "FK_FOLDER_USERS" FOREIGN KEY ("USERNAME")
	  REFERENCES "ADMINFILEMNG"."USERS" ("USERNAME") ENABLE;
