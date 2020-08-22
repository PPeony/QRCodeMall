-- auto-generated definition
create table admin
(
    admin_id       int auto_increment
        primary key,
    admin_name     varchar(45)  not null comment '名字',
    admin_password varchar(255) not null comment '密码'
)
    comment '管理员';

-- auto-generated definition
create table carousel
(
    carousel_id       int auto_increment
        primary key,
    carousel_img_name varchar(255)  null comment '图片名字',
    carousel_link     varchar(255)  null comment '链接',
    gmt_created       datetime      null,
    gmt_modified      datetime      null,
    is_deleted        int default 0 null
)
    comment '首页轮播图';

-- auto-generated definition
create table goods
(
    goods_id              int auto_increment
        primary key,
    goods_type_id         int            not null comment '商品类别，1蔬菜，2水果，3种子，4农药，5化肥，type表主键',
    goods_name            varchar(45)    not null,
    goods_price           decimal(10, 2) not null comment '一个套餐的价格',
    goods_type_name       varchar(45)    not null comment 'goods_type表的name',
    goods_qrcode_quantity int            not null comment '一个套餐的二维码数量',
    goods_picture         varchar(255)   null comment '商品图片',
    goods_detail          varchar(255)   null comment '详情',
    goods_introduction    text           not null comment '商品简介',
    goods_status          int default 0  not null comment '0上架，1下架',
    gmt_created           datetime       null,
    gmt_modified          datetime       null,
    is_deleted            int default 0  not null
)
    comment '商品表';

-- auto-generated definition
create table goods_type
(
    goods_type_id      int auto_increment
        primary key,
    goods_type_name    varchar(45)   not null comment '类别名字',
    goods_type_message text          null,
    is_deleted         int default 0 not null comment '0不删除'
)
    comment '商品种类表';

-- auto-generated definition
create table notice
(
    notice_id          int auto_increment
        primary key,
    notice_tittle_name varchar(255)  not null comment '标题名字',
    notice_message     text          not null comment '主要信息',
    gmt_created        datetime      not null,
    gmt_modified       datetime      null,
    is_deleted         int default 0 not null
)
    comment '公告表';

-- auto-generated definition
create table order_form
(
    order_form_id       int auto_increment
        primary key,
    user_id             int                         not null comment '用户表主键',
    user_address_id     int                         null comment '参照user_address',
    order_form_number   varchar(255)                not null comment '订单号',
    order_form_status   int            default 0    not null comment '0未付款，1已付款',
    order_form_price    decimal(10, 2) default 0.00 null,
    order_form_pay_type int                         null comment '支付方式，1支付宝，2微信',
    order_form_pay_time datetime                    null comment '支付时间',
    gmt_created         datetime                    not null,
    gmt_modified        datetime                    null,
    is_deleted          int            default 0    not null
)
    comment '订单表';

-- auto-generated definition
create table order_form_detail
(
    order_form_deatil_id  int auto_increment
        primary key,
    order_form_id         int            not null comment '参照order_form_id',
    goods_id              int            not null comment '商品表id',
    goods_name            varchar(45)    not null comment 'goods表',
    goods_price           decimal(10, 2) not null comment 'goods表',
    goods_qrcode_quantity int            not null comment 'goods表',
    goods_type_name       varchar(45)    not null comment 'goods表',
    gmt_created           datetime       not null,
    gmt_modified          datetime       null,
    is_deleted            int default 0  not null
)
    comment '订单详细信息';

-- auto-generated definition
create table property
(
    property_id     int unsigned auto_increment
        primary key,
    property_key    varchar(255)  not null comment '每个种类要有什么字段',
    goods_type_name varchar(45)   null,
    goods_type_id   int           not null,
    gmt_created     datetime      null,
    gmt_modified    datetime      null,
    is_deleted      int default 0 null
)
    comment '不同种类对应描述字段不同，比如农药会有标准，水果没有，所以本表用来设定每个种类应该有什么字段，另一个表为各个字段的具体描述';

-- auto-generated definition
create table property_value
(
    property_value_id int unsigned auto_increment
        primary key,
    property_id       int           not null comment '参照property表',
    qrcode_id         int           not null comment '参照qrcode表',
    property_key      varchar(255)  not null comment '字段名字',
    property_value    text          not null comment '具体字段描述',
    gmt_created       datetime      null,
    gmt_modified      datetime      null,
    is_deleted        int default 0 null
)
    comment '本表用来存储各个二维码扫出字段的描述';

-- auto-generated definition
create table qrcode
(
    qrcode_id       int auto_increment
        primary key,
    user_id         int           not null comment 'user表id',
    goods_id        int           not null comment 'goods表id',
    goods_type_id   int           not null comment 'goods_type表',
    goods_type_name varchar(45)   not null comment 'goods_type表',
    qrcode_link     varchar(255)  not null comment '二维码地址',
    gmt_created     datetime      not null,
    gmt_modified    datetime      null,
    is_deleted      int default 0 not null
)
    comment '二维码表';

-- auto-generated definition
create table user
(
    user_id                     int auto_increment
        primary key,
    user_name                   varchar(45)                      not null comment '用户名字不重复',
    user_phone                  varchar(45)                      null comment '用户手机号不重复',
    user_password               varchar(255)                     not null comment '用户密码',
    user_email                  varchar(45) default 'xxx@qq.com' null comment '用户邮箱不重复',
    is_vip                      int         default 0            not null comment '0不是VIP',
    user_point                  int         default 0            not null comment '积分',
    is_deleted                  int         default 0            not null,
    user_father_proxy_id        int                              null comment '父级代理id',
    user_father_proxy_name      varchar(45)                      null comment '父级代理名字',
    user_grandfather_proxy_id   int                              null comment '爷爷级代理id',
    user_grandfather_proxy_name varchar(45)                      null comment '爷爷级代理名字'
)
    comment '用户表';

-- auto-generated definition
create table user_address
(
    user_address_id       int auto_increment
        primary key,
    user_id               int           not null comment '参照user表',
    user_address_province varchar(45)   not null comment '省字段',
    user_address_city     varchar(45)   not null comment '市',
    user_address_district varchar(45)   not null comment '区',
    user_address_detail   varchar(45)   not null comment '详细地址',
    user_address_default  int default 0 not null comment '是否是默认地址，0不默认，1默认',
    gmt_created           datetime      not null,
    gmt_modified          datetime      null,
    is_deleted            int default 0 not null,
    receive_name          varchar(255)  not null,
    receive_phone         varchar(255)  not null
)
    comment '用户地址表';

-- auto-generated definition
create table user_bill
(
    user_bill_id        int auto_increment
        primary key,
    user_id             int            not null comment 'user主键',
    user_bill_money     decimal(10, 2) not null comment '一个账单花了多少钱',
    user_bill_direction int            null comment '0收入，1支出',
    user_bill_remark    varchar(255)   null comment '备注',
    gmt_created         datetime       null,
    gmt_modified        datetime       null,
    is_deleted          int default 0  not null
)
    comment '账单表';

