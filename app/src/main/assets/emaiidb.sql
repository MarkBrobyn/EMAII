DROP TABLE IF EXISTS "android_metadata";
CREATE TABLE "android_metadata" ("locale" TEXT DEFAULT en_us);
INSERT INTO "android_metadata" VALUES('en_us');
DROP TABLE IF EXISTS "items";
CREATE TABLE "items" ("datetime" DATETIME DEFAULT CURRENT_TIMESTAMP, "title" TEXT DEFAULT Title, "content" TEXT DEFAULT Content, "_id" INTEGER PRIMARY KEY  NOT NULL , "picture" BLOB DEFAULT null);
