--DROP TABLE zipkin_spans;
--DROP TABLE zipkin_annotations;
--DROP TABLE zipkin_dependencies;

CREATE TABLE IF NOT EXISTS zipkin_spans (
  `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 BIT traceIds instead of 64 bit',
  `trace_id` BIGINT NOT NULL,
  `id` BIGINT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `remote_service_name` VARCHAR(255),
  `parent_id` BIGINT,
  `debug` BIT(1),
  `start_ts` BIGINT COMMENT 'Span.timestamp(): epoch micros used FOR endTs QUERY AND TO implement TTL',
  `duration` BIGINT COMMENT 'Span.duration(): micros used FOR minDuration AND maxDuration query'
) ENGINE=INNODB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_spans ADD UNIQUE KEY(`trace_id_high`, `trace_id`, `id`) COMMENT 'ignore INSERT ON duplicate';
ALTER TABLE zipkin_spans ADD INDEX(`trace_id_high`, `trace_id`, `id`) COMMENT 'for joining WITH zipkin_annotations';
ALTER TABLE zipkin_spans ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTracesByIds';
ALTER TABLE zipkin_spans ADD INDEX(`name`);
ALTER TABLE zipkin_spans ADD INDEX(`remote_service_name`) COMMENT 'for getTraces AND getSpanNames';
ALTER TABLE zipkin_spans ADD INDEX(`start_ts`) COMMENT 'for getTraces ordering AND range';

CREATE TABLE IF NOT EXISTS zipkin_annotations (
  `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 BIT traceIds instead of 64 bit',
  `trace_id` BIGINT NOT NULL COMMENT 'coincides WITH zipkin_spans.trace_id',
  `span_id` BIGINT NOT NULL COMMENT 'coincides WITH zipkin_spans.id',
  `a_key` VARCHAR(255) NOT NULL COMMENT 'BinaryAnnotation.key OR Annotation.value IF TYPE == -1',
  `a_value` BLOB COMMENT 'BinaryAnnotation.value(), which must be smaller THAN 64KB',
  `a_type` INT NOT NULL COMMENT 'BinaryAnnotation.type() OR -1 IF Annotation',
  `a_timestamp` BIGINT COMMENT 'Used TO implement TTL; Annotation.timestamp OR zipkin_spans.timestamp',
  `endpoint_ipv4` INT COMMENT 'Null WHEN BINARY/Annotation.endpoint IS null',
  `endpoint_ipv6` BINARY(16) COMMENT 'Null WHEN BINARY/Annotation.endpoint IS NULL, OR NO IPv6 address',
  `endpoint_port` SMALLINT COMMENT 'Null WHEN BINARY/Annotation.endpoint IS null',
  `endpoint_service_name` VARCHAR(255) COMMENT 'Null WHEN BINARY/Annotation.endpoint IS null'
) ENGINE=INNODB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_annotations ADD UNIQUE KEY(`trace_id_high`, `trace_id`, `span_id`, `a_key`, `a_timestamp`) COMMENT 'Ignore INSERT ON duplicate';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`, `span_id`) COMMENT 'for joining WITH zipkin_spans';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTraces/ByIds';
ALTER TABLE zipkin_annotations ADD INDEX(`endpoint_service_name`) COMMENT 'for getTraces AND getServiceNames';
ALTER TABLE zipkin_annotations ADD INDEX(`a_type`) COMMENT 'for getTraces';
ALTER TABLE zipkin_annotations ADD INDEX(`a_key`) COMMENT 'for getTraces';

CREATE TABLE IF NOT EXISTS zipkin_dependencies (
  `day` DATE NOT NULL,
  `parent` VARCHAR(255) NOT NULL,
  `child` VARCHAR(255) NOT NULL,
  `call_count` BIGINT
) ENGINE=INNODB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_dependencies ADD UNIQUE KEY(`day`, `parent`, `child`);