--sys_dictionary
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('13','资源状态','Y','RESOURCE_STATE','资源状态','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('14','审核状态','Y','APPROVE_STATE','审核状态','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('15','操作类型','Y','OPERATE_TYPE','操作类型','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('16','资源类型','Y','RESOURCE_TYPE','资源类型','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('17','事件类型','Y','EVENT_TYPE','事件类型','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('18','华为返回码','Y','RETURN_CODE','华为返回码','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('19','商家类型','Y','MERCHANT_TYPE','商家类型','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('20','行业分类','Y','INDUSTRY_TYPE','行业分类','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('21','端口号批量申请最大数量','Y','PORT_MAX_NUM','端口号批量申请最大数量','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('22','线程池参数','Y','POOL_SIZE','线程池参数','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('23','信用代码最多使用商家数','Y','CREDITNUMBER_MAX','信用代码最多使用商家数','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('24','华为与系统的操作类型映射','Y','HW_OPERATE_TYPE','华为与系统的操作类型映射','valid','HuaWei');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('25','用于控制前端元素与VR的关联关系','Y','AUTH_FRONTEND','前端权限配置','valid','System');
insert into `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) values('26','用户允许登陆平台','Y','LOGIN_PLATFORM','登录平台','valid','System');

--sys_dict_item
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('22','1','未生效','valid','未生效','13');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('23','3','已生效','valid','已生效','13');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('24','4','已失效','valid','已失效','13');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('25','5','商家冻结','valid','商家冻结','13');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('26','6','运营冻结','valid','运营冻结','13');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('27','99','未提交','valid','未提交','14');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('28','1','待审核','valid','待审核','14');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('29','3','驳回','valid','驳回','14');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('30','4','审核通过','valid','审核通过','14');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('31','0','华为回调','valid','华为回调','15');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('32','1','创建','valid','创建','15');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('33','2','修改','valid','修改','15');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('34','6','商家冻结','valid','商家冻结','15');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('35','7','商家解冻','valid','商家解冻','15');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('36','3','删除','valid','删除','15');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('37','2','商家','valid','商家','16');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('38','3','端口号','valid','端口号','16');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('39','4','服务号','valid','服务号','16');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('40','5','服务号菜单','valid','服务号菜单','16');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('41','6','服务号主页','valid','服务号主页','16');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('42','7','商家消息模板','valid','商家消息模板','16');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('43','15','商家消息服务','valid','商家消息服务','16');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('44','1','资源状态变更','valid','资源状态变更','17');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('45','2','消息回执通知','valid','消息回执通知','17');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('46','100001','华为A2P提示：参数不合法','valid','华为A2P提示：参数不合法','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('47','100002','华为A2P提示：华为系统内部异常','valid','华为A2P提示：华为系统内部异常','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('48','120001','华为A2P提示：图片大小不合规','valid','华为A2P提示：图片大小不合规','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('49','120002','华为A2P提示：图片文件不存在','valid','华为A2P提示：图片文件不存在','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('50','120003','华为A2P提示：不合法的图片文件类型','valid','华为A2P提示：不合法的图片文件类型','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('51','120004','华为A2P提示：开发者已经被注册','valid','华为A2P提示：开发者已经被注册','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('52','120005','华为A2P提示：商家已经被注册','valid','华为A2P提示：商家已经被注册','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('53','120006','华为A2P提示：服务号已经被注册','valid','华为A2P提示：服务号已经被注册','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('54','120007','华为A2P提示：端口已经被注册','valid','华为A2P提示：端口已经被注册','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('55','120008','华为A2P提示：菜单已被注册','valid','华为A2P提示：菜单已被注册','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('56','120009','华为A2P提示：端口号已被其他服务号引用','valid','华为A2P提示：端口号已被其他服务号引用','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('57','120010','华为A2P提示：开发者不存在','valid','华为A2P提示：开发者不存在','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('58','120011','华为A2P提示：商家不存在','valid','华为A2P提示：商家不存在','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('59','120012','华为A2P提示：服务号不存在','valid','华为A2P提示：服务号不存在','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('60','120013','华为A2P提示：端口号不存在','valid','华为A2P提示：端口号不存在','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('61','120014','华为A2P提示：开发者未生效','valid','华为A2P提示：开发者未生效','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('62','120015','华为A2P提示：商家未生效','valid','华为A2P提示：商家未生效','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('63','120016','华为A2P提示：服务号未生效','valid','华为A2P提示：服务号未生效','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('64','120017','华为A2P提示：端口未生效','valid','华为A2P提示：端口未生效','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('65','120018','华为A2P提示：资源已经存在创建/修改审批流程，无法再发起创建/修改操作','valid','华为A2P提示：资源已经存在创建/修改审批流程，无法再发起创建/修改操作','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('66','120019','华为A2P提示：资源已经处于冻结状态或有冻结审批流程，无法再发起冻结操作','valid','华为A2P提示：资源已经处于冻结状态或有冻结审批流程，无法再发起冻结操作','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('67','120020','华为A2P提示：资源不在冻结状态或已经有解冻审批流程，无法进行解冻操作','valid','华为A2P提示：资源不在冻结状态或已经有解冻审批流程，无法进行解冻操作','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('68','120021','华为A2P提示：开发者的商家超过限制','valid','华为A2P提示：开发者的商家超过限制','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('69','120022','华为A2P提示：商家的服务号超过限制','valid','华为A2P提示：商家的服务号超过限制','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('70','120023','华为A2P提示：商家的端口号超过限制','valid','华为A2P提示：商家的端口号超过限制','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('71','120024','华为A2P提示：开发者的消息模板超过限制','valid','华为A2P提示：开发者的消息模板超过限制','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('72','120025','华为A2P提示：商家消息能力被冻结，无法创建消息模板','valid','华为A2P提示：商家消息能力被冻结，无法创建消息模板','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('73','120026','华为A2P提示：此类消息模板不允许输入可变参数','valid','华为A2P提示：此类消息模板不允许输入可变参数','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('74','120027','华为A2P提示：此类消息模板不允许填写图片','valid','华为A2P提示：此类消息模板不允许填写图片','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('75','120028','华为A2P提示：端口，服务号有效期不允许超过商家有效期','valid','华为A2P提示：端口，服务号有效期不允许超过商家有效期','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('76','120029','华为A2P提示：不允许提前有效期','valid','华为A2P提示：不允许提前有效期','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('77','120030','华为A2P提示：引用的图片类型不正确，比如不能将主页背景图片使用在服务号logo的位置','valid','华为A2P提示：引用的图片类型不正确，比如不能将主页背景图片使用在服务号logo的位置','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('78','160004','华为A2P提示：需要开通的用户还未开通服务，请先开通服务','valid','华为A2P提示：需要开通的用户还未开通服务，请先开通服务','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('79','160005','华为A2P提示：号码已被注册为白名单，但不是此开发者的白名单用户','valid','华为A2P提示：号码已被注册为白名单，但不是此开发者的白名单用户','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('80','160006','华为A2P提示：此号码未在规定时间内进行测试验证确认，添加失败','valid','华为A2P提示：此号码未在规定时间内进行测试验证确认，添加失败','18');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('81','1','企业','valid','企业','19');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('82','2','党政&国家机关','valid','党政&国家机关','19');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('83','3','事业单位','valid','事业单位','19');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('84','4','个体工商户','valid','个体工商户','19');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('85','5','民办非企业单位','valid','民办非企业单位','19');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('86','6','社会团体','valid','社会团体','19');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('87','1','金融理财','valid','金融理财','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('88','2','社交通讯','valid','社交通讯','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('89','3','影音娱乐','valid','影音娱乐','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('90','4','旅游出行','valid','旅游出行','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('91','5','购物','valid','购物','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('92','6','本地生活','valid','本地生活','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('93','7','运动健康','valid','运动健康','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('94','8','教育培训','valid','教育培训','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('95','9','新闻阅读','valid','新闻阅读','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('96','10','运营商','valid','运营商','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('97','11','其他','valid','其他','20');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('98','1','端口号批量申请最大数量','valid','500','21');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('100','1','核心线程数','valid','6','22');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('101','2','最大线程数','valid','8','22');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('102','1','信用代码最多使用商家数','valid','5','23');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('103','1','创建','valid','1','24');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('104','2','修改','valid','2','24');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('105','3','运营冻结','valid','8','24');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('106','4','运营解冻','valid','9','24');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('107','5','商家冻结','valid','6','24');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('108','6','商家解冻','valid','7','24');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('109','8','运营冻结','valid','运营冻结','15');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('110','9','运营解冻','valid','运营解冻','15');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('111','5','提交预审','valid','提交预审','14');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('112','6','预审驳回','valid','预审驳回','14');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('113','MERCHANT_MENU','商家管理菜单','valid','商家查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('115','MERCHANT_QUERY','商家查看','valid','商家查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('116','MERCHANT_CREATE','商家创建','valid','商家入驻','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('117','OVERVIEW_MENU','总览菜单','valid','商家查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('118','PORT_MENU','端口号管理菜单','valid','端口号查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('119','SERVICENUM_MENU','服务号管理菜单','valid','服务号查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('120','HOMEPAGE_MENU','主页管理菜单','valid','主页查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('121','MENU_MENU','菜单管理菜单','valid','菜单查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('122','ORGANIZATION_MENU','组织结构管理菜单','valid','组织查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('123','ROLE_MENU','权限分组菜单','valid','权限分组查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('124','ACCOUNT_MENU','账户管理菜单','valid','查看账户列表','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('125','MERCHANT_Modify','商家修改','valid','商家修改','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('126','PORT_CREATE','端口号创建','valid','端口号创建','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('127','PORT_QUERY','端口号查看','valid','端口号查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('128','PORT_Modify','端口号修改','valid','端口号修改','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('129','PORT_Delete','端口号删除','valid','端口号删除','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('130','SERVICENUM_CREATE','服务号创建','valid','服务号创建','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('131','SERVICENUM_MODIFY','服务号修改','valid','服务号修改','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('133','PORT_MODIFY','端口号修改','valid','端口号修改','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('136','PORT_DELETE','端口号删除','valid','端口号删除','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('139','MERCHANT_MODIFY','商家修改','valid','商家修改','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('141','ACCOUNT_QUERY','账户查看','valid','查看账户列表','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('144','ACCOUNT_CREATE','账户新增','valid','新增账户','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('147','ACCOUNT_MODIFY','账户修改','valid','修改账户','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('150','ACCOUNT_DELETE','账户删除','valid','删除账户','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('155','USER_FORCED','用户下线','valid','用户下线','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('158','PORTAL_QUERY','主页查看','valid','主页查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('161','PORTAL_CREATE','主页创建','valid','主页创建','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('164','PORTAL_MODIFY','主页修改','valid','主页修改','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('167','PORTAL_DELETE','主页删除','valid','主页删除','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('170','PORTAL_CLONE','主页克隆','valid','主页克隆','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('173','MENU_QUERY','菜单查看','valid','菜单查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('176','MENU_MODIFY','菜单修改','valid','菜单修改','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('179','MENU_DELETE','菜单删除','valid','菜单删除','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('182','MENU_CLONE','菜单克隆','valid','菜单克隆','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('185','AUTH_ADD','用户授权','valid','用户授权','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('188','AUTH_DELETE','删除授权','valid','删除授权','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('191','PERMISSIONS_QUERY','权限分组查看','valid','权限分组查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('194','PERMISSIONS_MODIFY','权限分组修改','valid','权限分组修改','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('197','PERMISSIONS_DELETE','权限分组删除','valid','权限分组删除','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('200','PERMISSIONS_CREATE','权限分组新增','valid','权限分组新增','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('203','ORG_QUERY','组织查看','valid','组织查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('206','ORG_CREATE','组织新增','valid','组织新增','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('209','ORG_DISABLE','组织禁用','valid','组织禁用','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('212','ORG_UPDATE','组织更新','valid','组织更新','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('213','MENU_CREATE','菜单创建','valid','菜单创建','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('216','SYN_RESOURCE','同步资源','valid','同步资源','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('219','MERCHANT_DELETE','商家删除','valid','商家删除','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('222','SERVICENUM_QUERY','服务号查看','valid','服务号查看','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('225','SERVICENUM_DELETE','服务号删除','valid','服务号删除','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('228','MERCHANT_FREEZE','商家冻结','valid','商家冻结','25');
insert into `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) values('231','ORG_REFRESH','组织刷新','valid','组织刷新','25');
insert into `sys_dict_item` (`id`,`key`, `name`, `status`, `value`, `dict_id`) values('233','EC','服务号EC平台','valid','EC','26');
insert into `sys_dict_item` (`id`,`key`, `name`, `status`, `value`, `dict_id`) values('234','MANAGER','服务号管理平台','valid','MANAGER','26');