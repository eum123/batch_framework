
CREATE TABLE `TB_BATCH` (
  `BATCH_ID` varchar(50) NOT NULL COMMENT 'batch id',
  `BATCH_IP` varchar(15) NOT NULL COMMENT 'JMX IP',
  `BATCH_PORT` int(6) unsigned NOT NULL COMMENT 'JMX PORT',
  `REGIST_DATETIME` datetime NOT NULL COMMENT '등록시간',
  `UPDATE_DATETIME` datetime NOT NULL COMMENT '수정시간',
  `IS_USAGE` tinyint(1) NOT NULL COMMENT '사용여부',
  PRIMARY KEY (`BATCH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='batch process 정보';



CREATE TABLE `TB_BATCH_JOB` (
  `BATCH_JOB_ID` varchar(50) NOT NULL COMMENT 'batch job id',
  `BATCH_ID` varchar(50) NOT NULL COMMENT 'batch id',
  `JOB_SCHEDULE` varchar(15) NOT NULL COMMENT '배치 실행 주기',
  `IS_USAGE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '사용여부',
  `IS_SINGLE_TRANSACTION` tinyint(1) NOT NULL DEFAULT '0' COMMENT '단일 transaction 처리 여부',
  `READER_CLASS` varchar(100) NOT NULL COMMENT 'reader class name',
  `READER_PROPERTIES` varchar(500) DEFAULT NULL COMMENT 'reader class 설정값',
  `WORKER_CLASS` varchar(100) NOT NULL COMMENT 'worker class name',
  `WORKER_PROPERTIES` varchar(500) DEFAULT NULL COMMENT 'worker class 설정값',
  `WRITER_CLASS` varchar(100) NOT NULL COMMENT 'writer class name',
  `WRITER_PROPERTIES` varchar(500) DEFAULT NULL COMMENT 'writer class 설정값',
  `START_DATETIME` datetime DEFAULT NULL COMMENT 'batch 시작 시간',
  `END_DATETIME` datetime DEFAULT NULL COMMENT 'batch 종료 시간',
  `EXECUTE_RESULT` char(1) COMMENT 'batch 실행 결과',
  `ERROR_MESSAGE` varchar(4000) DEFAULT NULL COMMENT '오류 메세지',
  `REGIST_DATETIME` datetime NOT NULL COMMENT '등록시간',
  `UPDATE_DATETIME` datetime NOT NULL COMMENT '수정시간',
  PRIMARY KEY (`BATCH_JOB_ID`,`BATCH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='batch job 정보';


CREATE TABLE `TB_BATCH_HISTORY` (
  `SEQ` int(10) NOT NULL AUTO_INCREMENT COMMENT '자동증가',
  `BATCH_JOB_ID` varchar(50) NOT NULL COMMENT 'batch job id',
  `BATCH_ID` varchar(50) NOT NULL COMMENT 'batch id',
  `START_DATETIME` datetime DEFAULT NULL COMMENT 'batch 시작 시간',
  `END_DATETIME` datetime DEFAULT NULL COMMENT 'batch 종료 시간',
  `EXECUTE_RESULT` char(1) COMMENT 'batch 실행 결과',
  `ERROR_MESSAGE` varchar(4000) DEFAULT NULL COMMENT '오류 메세지',
  `REGIST_DATETIME` datetime NOT NULL COMMENT '등록시간',
  `UPDATE_DATETIME` datetime NOT NULL COMMENT '수정시간',
  PRIMARY KEY (`SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='batch job history';




insert into TB_BATCH (BATCH_ID, BATCH_IP, BATCH_PORT, REGIST_DATETIME, UPDATE_DATETIME, IS_USAGE)
values('test', '127.0.0.1', 7000, now(), now(), true);

INSERT into TB_BATCH_JOB (BATCH_JOB_ID, BATCH_ID, JOB_SCHEDULE, IS_USAGE, IS_SINGLE_TRANSACTION, READER_CLASS, READER_PROPERTIES, WORKER_CLASS,
                          WORKER_PROPERTIES, WRITER_CLASS, WRITER_PROPERTIES, START_DATETIME, END_DATETIME, EXECUTE_RESULT, ERROR_MESSAGE, REGIST_DATETIME, UPDATE_DATETIME)
VALUES ('test_job_1', 'test', '0 * * * * * *', true, false, '', '', '', '', '', '', null, NULL, '', '', now(), now());