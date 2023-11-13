create database user_universe;
use user_universe;
-- auto-generated definition
create table user
(
    id              bigint auto_increment comment 'id'
        primary key,
    username        varchar(256)                           null comment '用户昵称',
    user_account    varchar(256)                           null comment '账号',
    user_password   varchar(256)                           not null comment '密码',
    avatar_url      varchar(1024)                          null comment '用户头像',
    gender          tinyint      default 0                 null comment '性别 0-男 1-女',
    phone           varchar(128)                           null comment '电话',
    email           varchar(512)                           null comment '邮箱',
    user_status     int          default 0                 null comment '用户状态 0-正常',
    user_role       tinyint      default 0                 not null comment '用户角色 0-普通用户 1-管理员',
    invitation_code varchar(128) default '666666'          null comment '邀请码',
    create_time     datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time     datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete       tinyint      default 0                 not null comment '是否删除 0-否'
)
    comment '用户表';

