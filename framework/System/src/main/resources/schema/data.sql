--sys_dictionary
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (13, '资源状态', 'Y', 'RESOURCE_STATE', '资源状态', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (14, '审核状态', 'Y', 'APPROVE_STATE', '审核状态', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (15, '操作类型', 'Y', 'OPERATE_TYPE', '操作类型', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (16, '资源类型', 'Y', 'RESOURCE_TYPE', '资源类型', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (17, '事件类型', 'Y', 'EVENT_TYPE', '事件类型', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (18, '华为返回码', 'Y', 'RETURN_CODE', '华为返回码', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (19, '商家类型', 'Y', 'MERCHANT_TYPE', '商家类型', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (20, '行业分类', 'Y', 'INDUSTRY_TYPE', '行业分类', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (21, '端口号批量申请最大数量', 'Y', 'PORT_MAX_NUM', '端口号批量申请最大数量', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (22, '线程池参数', 'Y', 'POOL_SIZE', '线程池参数', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (23, '信用代码最多使用商家数', 'Y', 'CREDITNUMBER_MAX', '信用代码最多使用商家数', 'valid', 'System');
INSERT INTO `sys_dictionary` (`id`, `desc`, `editable`, `group`, `name`, `status`, `type`) VALUES (24, '华为与系统的操作类型映射', 'Y', 'HW_OPERATE_TYPE', '华为与系统的操作类型映射', 'valid', 'HuaWei');


--sys_dict_item
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (22, '1', '未生效', 'valid', '未生效', 13);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (23, '3', '已生效', 'valid', '已生效', 13);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (24, '4', '已失效', 'valid', '已失效', 13);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (25, '5', '商家冻结', 'valid', '商家冻结', 13);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (26, '6', '运营冻结', 'valid', '运营冻结', 13);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (27, '99', '未提交', 'valid', '未提交', 14);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (28, '1', '待审核', 'valid', '待审核', 14);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (29, '3', '驳回', 'valid', '驳回', 14);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (30, '4', '审核通过', 'valid', '审核通过', 14);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (31, '0', '华为回调', 'valid', '华为回调', 15);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (32, '1', '创建', 'valid', '创建', 15);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (33, '2', '修改', 'valid', '修改', 15);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (34, '6', '冻结', 'valid', '冻结', 15);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (35, '7', '解冻', 'valid', '解冻', 15);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (36, '3', '删除', 'valid', '删除', 15);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (37, '2', '商家', 'valid', '商家', 16);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (38, '3', '端口号', 'valid', '端口号', 16);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (39, '4', '服务号', 'valid', '服务号', 16);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (40, '5', '服务号菜单', 'valid', '服务号菜单', 16);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (41, '6', '服务号主页', 'valid', '服务号主页', 16);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (42, '7', '商家消息模板', 'valid', '商家消息模板', 16);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (43, '15', '商家消息服务', 'valid', '商家消息服务', 16);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (44, '1', '资源状态变更', 'valid', '资源状态变更', 17);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (45, '2', '消息回执通知', 'valid', '消息回执通知', 17);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (46, '100001', '华为A2P提示：参数不合法', 'valid', '华为A2P提示：参数不合法', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (47, '100002', '华为A2P提示：华为系统内部异常', 'valid', '华为A2P提示：华为系统内部异常', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (48, '120001', '华为A2P提示：图片大小不合规', 'valid', '华为A2P提示：图片大小不合规', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (49, '120002', '华为A2P提示：图片文件不存在', 'valid', '华为A2P提示：图片文件不存在', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (50, '120003', '华为A2P提示：不合法的图片文件类型', 'valid', '华为A2P提示：不合法的图片文件类型', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (51, '120004', '华为A2P提示：开发者已经被注册', 'valid', '华为A2P提示：开发者已经被注册', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (52, '120005', '华为A2P提示：商家已经被注册', 'valid', '华为A2P提示：商家已经被注册', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (53, '120006', '华为A2P提示：服务号已经被注册', 'valid', '华为A2P提示：服务号已经被注册', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (54, '120007', '华为A2P提示：端口已经被注册', 'valid', '华为A2P提示：端口已经被注册', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (55, '120008', '华为A2P提示：菜单已被注册', 'valid', '华为A2P提示：菜单已被注册', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (56, '120009', '华为A2P提示：端口号已被其他服务号引用', 'valid', '华为A2P提示：端口号已被其他服务号引用', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (57, '120010', '华为A2P提示：开发者不存在', 'valid', '华为A2P提示：开发者不存在', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (58, '120011', '华为A2P提示：商家不存在', 'valid', '华为A2P提示：商家不存在', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (59, '120012', '华为A2P提示：服务号不存在', 'valid', '华为A2P提示：服务号不存在', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (60, '120013', '华为A2P提示：端口号不存在', 'valid', '华为A2P提示：端口号不存在', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (61, '120014', '华为A2P提示：开发者未生效', 'valid', '华为A2P提示：开发者未生效', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (62, '120015', '华为A2P提示：商家未生效', 'valid', '华为A2P提示：商家未生效', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (63, '120016', '华为A2P提示：服务号未生效', 'valid', '华为A2P提示：服务号未生效', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (64, '120017', '华为A2P提示：端口未生效', 'valid', '华为A2P提示：端口未生效', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (65, '120018', '华为A2P提示：资源已经存在创建/修改审批流程，无法再发起创建/修改操作', 'valid', '华为A2P提示：资源已经存在创建/修改审批流程，无法再发起创建/修改操作', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (66, '120019', '华为A2P提示：资源已经处于冻结状态或有冻结审批流程，无法再发起冻结操作', 'valid', '华为A2P提示：资源已经处于冻结状态或有冻结审批流程，无法再发起冻结操作', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (67, '120020', '华为A2P提示：资源不在冻结状态或已经有解冻审批流程，无法进行解冻操作', 'valid', '华为A2P提示：资源不在冻结状态或已经有解冻审批流程，无法进行解冻操作', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (68, '120021', '华为A2P提示：开发者的商家超过限制', 'valid', '华为A2P提示：开发者的商家超过限制', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (69, '120022', '华为A2P提示：商家的服务号超过限制', 'valid', '华为A2P提示：商家的服务号超过限制', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (70, '120023', '华为A2P提示：商家的端口号超过限制', 'valid', '华为A2P提示：商家的端口号超过限制', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (71, '120024', '华为A2P提示：开发者的消息模板超过限制', 'valid', '华为A2P提示：开发者的消息模板超过限制', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (72, '120025', '华为A2P提示：商家消息能力被冻结，无法创建消息模板', 'valid', '华为A2P提示：商家消息能力被冻结，无法创建消息模板', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (73, '120026', '华为A2P提示：此类消息模板不允许输入可变参数', 'valid', '华为A2P提示：此类消息模板不允许输入可变参数', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (74, '120027', '华为A2P提示：此类消息模板不允许填写图片', 'valid', '华为A2P提示：此类消息模板不允许填写图片', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (75, '120028', '华为A2P提示：端口，服务号有效期不允许超过商家有效期', 'valid', '华为A2P提示：端口，服务号有效期不允许超过商家有效期', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (76, '120029', '华为A2P提示：不允许提前有效期', 'valid', '华为A2P提示：不允许提前有效期', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (77, '120030', '华为A2P提示：引用的图片类型不正确，比如不能将主页背景图片使用在服务号logo的位置', 'valid', '华为A2P提示：引用的图片类型不正确，比如不能将主页背景图片使用在服务号logo的位置', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (78, '160004', '华为A2P提示：需要开通的用户还未开通服务，请先开通服务', 'valid', '华为A2P提示：需要开通的用户还未开通服务，请先开通服务', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (79, '160005', '华为A2P提示：号码已被注册为白名单，但不是此开发者的白名单用户', 'valid', '华为A2P提示：号码已被注册为白名单，但不是此开发者的白名单用户', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (80, '160006', '华为A2P提示：此号码未在规定时间内进行测试验证确认，添加失败', 'valid', '华为A2P提示：此号码未在规定时间内进行测试验证确认，添加失败', 18);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (81, '1', '企业', 'valid', '企业', 19);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (82, '2', '党政&国家机关', 'valid', '党政&国家机关', 19);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (83, '3', '事业单位', 'valid', '事业单位', 19);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (84, '4', '个体工商户', 'valid', '个体工商户', 19);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (85, '5', '民办非企业单位', 'valid', '民办非企业单位', 19);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (86, '6', '社会团体', 'valid', '社会团体', 19);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (87, '1', '金融理财', 'valid', '金融理财', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (88, '2', '社交通讯', 'valid', '社交通讯', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (89, '3', '影音娱乐', 'valid', '影音娱乐', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (90, '4', '旅游出行', 'valid', '旅游出行', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (91, '5', '购物', 'valid', '购物', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (92, '6', '本地生活', 'valid', '本地生活', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (93, '7', '运动健康', 'valid', '运动健康', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (94, '8', '教育培训', 'valid', '教育培训', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (95, '9', '新闻阅读', 'valid', '新闻阅读', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (96, '10', '运营商', 'valid', '运营商', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (97, '11', '其他', 'valid', '其他', 20);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (98, '1', '端口号批量申请最大数量', 'valid', '500', 21);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (100, '1', '核心线程数', 'valid', '5', 22);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (101, '2', '最大线程数', 'valid', '8', 22);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (102, '1', '信用代码最多使用商家数', 'valid', '5', 23);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (103, '1', '创建', 'valid', '1', 24);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (104, '2', '修改', 'valid', '2', 24);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (105, '3', '运营冻结', 'valid', '8', 24);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (106, '4', '运营解冻', 'valid', '9', 24);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (107, '5', '商家冻结', 'valid', '6', 24);
INSERT INTO `sys_dict_item` (`id`, `key`, `name`, `status`, `value`, `dict_id`) VALUES (108, '6', '商家解冻', 'valid', '7', 24);