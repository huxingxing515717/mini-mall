CREATE TABLE `acc_statement`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `bill_number` varchar(38) NOT NULL COMMENT '单号',
  `state` varchar(16) NOT NULL COMMENT '业务状态',
  `pay_state` varchar(16) NOT NULL COMMENT '付款状态',
  `store_uuid` varchar(38) NOT NULL COMMENT '项目uuid',
  `tenant_uuid` varchar(38) NOT NULL COMMENT '商户uuid',
  `contract_uuid` varchar(38) NOT NULL COMMENT '合同uuid',
  `account_date` date NOT NULL COMMENT '记账日期',
  `sales_rate` numeric(19, 4) NOT NULL COMMENT '销售提成率',
  `total` numeric(19, 2) NOT NULL COMMENT '账单总金额',
  `tax` numeric(19, 2) NOT NULL COMMENT '账单总税额',
  PRIMARY KEY (`uuid`),
  INDEX `idx_statement_1`(`contract_uuid`),
  INDEX `idx_statement_2`(`store_uuid`, `tenant_uuid`)
) COMMENT = '账单主表';

CREATE TABLE `acc_statement_detail`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `statement_uuid` varchar(38) NOT NULL COMMENT '账单主表uuid',
  `line_number` int(8) NOT NULL COMMENT '行号',
  `begin_date` date NOT NULL COMMENT '结算起始日期',
  `end_date` date NOT NULL COMMENT '结算结束日期',
  `total` numeric(19, 2) NOT NULL COMMENT '本次结算金额',
  `tax` numeric(19, 2) NOT NULL COMMENT '本次结算税额',
  `sales_total` numeric(19, 2) NOT NULL COMMENT '本次销售提成总额',
  `sales_tax` numeric(19, 2) NOT NULL COMMENT '本次销售提成税额',
  `subject_uuid` varchar(38) NOT NULL COMMENT '科目uuid',
  `tax_rate` numeric(19, 4) NOT NULL COMMENT '税率',
  PRIMARY KEY (`uuid`),
  INDEX `idx_smdetail_1`(`statement_uuid`, `line_number`)
) COMMENT = '账单明细表';

CREATE TABLE `acc_subject`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `tax_rate` numeric(6, 4) NOT NULL COMMENT '税率',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_subject_1`(`code`)
) COMMENT = '科目表';

CREATE TABLE `basis_operationlog`  ();

CREATE TABLE `basis_stock`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `entity_key` varchar(255) NOT NULL COMMENT '实体唯一键',
  `warehouse` varchar(255) NOT NULL COMMENT '仓库',
  `quantity` int(11) NOT NULL COMMENT '库存数量',
  PRIMARY KEY (`uuid`),
  INDEX `idx_stock_1`(`entity_key`)
) COMMENT = '库存表';

CREATE TABLE `invest_biztype`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_biztype_1`(`code`)
) COMMENT = '业态表';

CREATE TABLE `invest_brand`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_brand_1`(`code`)
) COMMENT = '品牌表';

CREATE TABLE `invest_building`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `store_uuid` varchar(38) NOT NULL COMMENT '项目uuid',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_building_1`(`store_uuid`)
) COMMENT = '楼宇表';

CREATE TABLE `invest_contract`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `serial_number` varchar(32) NOT NULL COMMENT '合同号',
  `signboard` varchar(255) NOT NULL COMMENT '店招',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `store_uuid` varchar(38) NOT NULL COMMENT '项目uuid（甲方）',
  `tenant_uuid` varchar(38) NOT NULL COMMENT '商户uuid（乙方）',
  `building_uuid` varchar(38) NOT NULL COMMENT '楼宇uuid',
  `floor_uuid` varchar(38) NOT NULL COMMENT '楼层uuid',
  `position_uuid` varchar(38) NOT NULL COMMENT '铺位uuid',
  `brand_uuid` varchar(38) NOT NULL COMMENT '品牌uuid',
  `biztype_uuid` varchar(38) NOT NULL COMMENT '业态uuid',
  `begin_date` date NOT NULL COMMENT '合同起始日期',
  `end_date` date NOT NULL COMMENT '合同结束日期',
  `month_rent` numeric(19, 2) NOT NULL COMMENT '月租金',
  `subject_uuid` varchar(38) NOT NULL COMMENT '科目uuid',
  `tax_rate` numeric(19, 4) NULL COMMENT '科目税率',
  `sales_rate` numeric(19, 4) NOT NULL COMMENT '销售提成率',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_contract_1`(`store_uuid`, `tenant_uuid`)
) COMMENT = '合同表';

CREATE TABLE `invest_floor`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `store_uuid` varchar(38) NOT NULL COMMENT '项目uuid',
  `building_uuid` varchar(38) NOT NULL COMMENT '楼宇uuid',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_floor_1`(`store_uuid`, `building_uuid`)
) COMMENT = '楼层表';

CREATE TABLE `invest_position`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `store_uuid` varchar(38) NOT NULL COMMENT '项目uuid',
  `building_uuid` varchar(38) NOT NULL COMMENT '楼宇uuid',
  `floor_uuid` varchar(38) NOT NULL COMMENT '楼层uuid',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_position_1`(`store_uuid`, `building_uuid`, `floor_uuid`)
) COMMENT = '位置表';

CREATE TABLE `invest_settle_detail`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `store_uuid` varchar(38) NOT NULL COMMENT '项目uuid',
  `tenant_uuid` varchar(38) NOT NULL COMMENT '商户uuid',
  `contract_uuid` varchar(38) NOT NULL COMMENT '合同uuid',
  `begin_date` date NOT NULL COMMENT '账期开始日期',
  `end_date` date NOT NULL COMMENT '账期结束日期',
  `total` numeric(19, 2) NOT NULL COMMENT '本期出账金额',
  `tax` numeric(19, 2) NOT NULL COMMENT '本期出账税额',
  `subject_uuid` varchar(38) NOT NULL COMMENT '科目uuid',
  `sales_rate` numeric(19, 4) NOT NULL COMMENT '销售提成率',
  `tax_rate` numeric(19, 4) NOT NULL COMMENT '科目税率',
  `statement_uuid` varchar(38) NULL COMMENT '账单uuid，为-表示未出账',
  PRIMARY KEY (`uuid`),
  INDEX `idx_detail_1`(`contract_uuid`),
  INDEX `idx_detail_2`(`store_uuid`, `tenant_uuid`)
) COMMENT = '合同结算明细表';

CREATE TABLE `invest_store`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_store_1`(`code`)
) COMMENT = '项目表';

CREATE TABLE `invest_tenant`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_tenant_1`(`code`)
) COMMENT = '商戶表';

CREATE TABLE `prod_goods`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_goods_1`(`code`)
) COMMENT = '商品表';

CREATE TABLE `prod_goods_inbound`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `bill_number` varchar(32) NOT NULL COMMENT '入库单号',
  `state` varchar(16) NOT NULL COMMENT '业务状态',
  `inbound_date` date NOT NULL COMMENT '入库日期',
  `warehouse` varchar(255) NOT NULL COMMENT '仓库',
  `goods_uuids` varchar(4056) NOT NULL COMMENT '商品uuid集合',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_inbound_1`(`bill_number`)
) COMMENT = '商品入库表';

CREATE TABLE `prod_inbound_detail`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `goods_inbound_uuid` varchar(38) NOT NULL COMMENT '入库单uuid',
  `line_number` int(8) NOT NULL COMMENT '行号',
  `goods_uuid` varchar(38) NOT NULL COMMENT '商品uuid',
  `quantity` int(11) NOT NULL COMMENT '入库数量',
  `warehouse_qty` int(11) NOT NULL COMMENT '库存数量',
  PRIMARY KEY (`uuid`),
  INDEX `idx_detail_1`(`goods_inbound_uuid`, `line_number`),
  INDEX `idx_detail_2`(`goods_uuid`)
) COMMENT = '商品入库明细表';

CREATE TABLE `sales_input`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `bill_number` varchar(32) NOT NULL COMMENT '单号',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `store_uuid` varchar(38) NOT NULL COMMENT '项目uuid',
  `tenant_uuid` varchar(38) NOT NULL COMMENT '商户uuid',
  `contract_uuid` varchar(38) NOT NULL COMMENT '合同uuid',
  `payment_type_uuid` varchar(38) NOT NULL COMMENT '付款方式uuid',
  `pay_total` numeric(19, 2) NOT NULL COMMENT '付款金额',
  `goods_uuids` varchar(4096) NOT NULL COMMENT '商品uuid集合',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_input_1`(`bill_number`),
  INDEX `idx_input_2`(`store_uuid`)
) COMMENT = '销售数据录入表';

CREATE TABLE `sales_input_detail`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `input_uuid` varchar(38) NOT NULL COMMENT '主表uuid',
  `line_number` int(8) NOT NULL COMMENT '行号',
  `sales_date` date NOT NULL COMMENT '销售日期',
  `goods_uuid` varchar(38) NOT NULL COMMENT '商品uuid',
  `warehouse_qty` int(11) NOT NULL COMMENT '商品仓库库存',
  `warehouse` varchar(255) NOT NULL COMMENT '仓库',
  `quantity` int(11) NOT NULL COMMENT '本次销售数量',
  `total` numeric(19, 2) NOT NULL COMMENT '销售金额',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_inputdetail_1`(`input_uuid`)
) COMMENT = '销售明细表';

CREATE TABLE `sales_paymenttype`  (
  `uuid` varchar(38) NOT NULL COMMENT '唯一标识',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `remark` varchar(1024) NULL COMMENT '说明',
  PRIMARY KEY (`uuid`),
  INDEX `idx_paytype_1`(`code`)
) COMMENT = '付款方式表';

