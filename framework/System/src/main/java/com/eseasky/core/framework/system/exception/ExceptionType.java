package com.eseasky.core.framework.system.exception;


import lombok.Data;

@Data
public class ExceptionType {
	// 系统字典操作异常
	public static String[] SYSTEMHASSAMEDICTITEM = {"90000001", "字典项枚举值重复"}; 
	public static String[] SYSTEMNOEXISTSDICT = {"90000002", "字典不存在"}; 
	public static String[] SYSTEMDICTISINVALID = {"90000003", "字典状态为无效状态"};
	public static String[] SYSTEMDICTDELETEERROR = {"90000004", "字典删除异常"};
	public static String[] SYSTEMDICTDNOITEMS = {"90000005", "字典没有字典项"};
	public static String[] SYSTEMDICTDREMOVEITEMERROR = {"90000006", "字典项删除失败"};
	public static String[] SYSTEEXISTSDICT  = {"90000007", "字典类型+字典分组已经存在"};
	public static String[] SYSTEFieldTooLong  = {"90000008", "字典值中字段长度超过最大限制"};


	// 订单操作异常
	public static String[] ORDER_CREATE_USER_ISNULL = {"80000001", "订单创建用户为空"};
	public static String[] ORDER_CREATE_EXCEPTION = {"800000012", "订单创建失败"};
	public static String[] ORDERCREATEUSERISVALID = {"80000002", "订单创建用户校验失败"};
	public static String[] ORDER_CREATE_ORDERINFO_ISNULL = {"80000003", "订单创建，订单明细校验失败"};
	public static String[] ORDER_CREATE_OCCUPY_ERROR = {"80000004", "资源预占失败"};
	public static String[] ORDERCREATEPRICEPLANNOTSET = {"80000005", "未设置定价计划"};
	public static String[] ORDERCREATECALCEXCETION = {"80000006", "费用计算异常"};
	public static String[] ORDERCREATEORDERLINEEXCEPTION = {"80000007", "订单行创建异常"};
	public static String[] ORDER_CREATE_RES_IS_OCCUPIED = {"80000009", "资源已经被占用"};
	public static String[] ORDER_CREATE_GOODS_IS_NULL = {"80000010", "订购商品为空"};
	public static String[] ORDER_CREATE_GOODS_NOT_EXISTS = {"80000011", "订购商品不存在"};
	public static String[] ORDER_INSERT_LEASE_PAY_INFO_ERROR = {"80000012", "退费支付信息插入失败"};
	public static String[] ORDER_MODIFY_PAYINFO_STATUS_ERROR = {"80000013", "退租修改订单表和订单支付信息表失败"};
	public static String[] ORDER_ORDER_CANCEL_ERROR = {"80000014", "用户主动取消订单失败"};
	public static String[] ORDER_ORDER_CALC_TIME_UNIT_ERROR = {"80000015", "算费错误，时间单位错误"};
	public static String[] ORDER_RENEWALED_NOT_RENEWAL = {"80000015", "已经续租过的订单不能再续租"};
	public static String[] ORDER_CONFERENCE_NOT_RENEWAL = {"80000016", "原订单是会议室，不能续订"};
	public static String[] ORDER_OFFLINE_NOT_RENEWAL = {"80000017", "原订单是线下订单，不能续订"};
	// 资源操作
	public static String[] RESOURCE_OCCUPY_DELETE_ERROR = {"70000001", "资源占用清除失败"};
	public static String[] RESOURCE_GENERAL_REPORT_ERROR = {"70000002", "报表生成错误"};
	public static String[] RESOURCE_CHANGE_ERROR = {"70000003", "资源转换错误"};
	public static String[] RESOURCE_FREE_NOT_REFUND = {"70000004", "附赠资源不能退租"};
	// 支付信息处理
	public static String[] PAY_BACK_INFO_NOT_COMPLETE = {"60000001", "支付信息不完整"};
	public static String[] PAY_ORDER_NOT_PAY_STATUS = {"60000002", "订单不在支付状态"};
	public static String[] PAY_NOT_FOUND_LEASE = {"60000003", "未找到支付费用信息"};
	public static String[] PAY_VERIFY_SIGN_FALSE = {"60000004", "验证签名失败"};
	public static String[] REFUND_MODIFY_OLDORDER_FALSE = {"60000005", "退款后对原支付orderCode的业务处理失败"};
	public static String[] REFUND_INTERFACE_ERROR = {"60000006", "调用第三方支付系统的退款接口返回有报错"};
	public static String[] REFUND_PAYINFO_ERROR = {"60000007", "退租异常，未查询到支付信息"};
	// 任务处理
	public static String[] TASK_NOT_ALREADY_CLAIM = {"500000001", "任务不存在或者已经被领取"};
	public static String[] TASK_INFO_NOT_COMPLETE = {"500000002", "任务提交信息不正确"};
	public static String[] TASK_USER_NOT_FOUND = {"500000003", "任务未找到可用用户"};
	public static String[] TASK_NOT_FOUND = {"500000004", "未找到可用任务"};
	public static String[] TASK_NOT_FOUND_REFUND = {"500000005", "未找到可退款订单"};
	public static String[] TASK_NOT_FOUND_REFUND_TASK = {"500000006", "未找到可退款订单任务"};
	public static String[] TASK_CLAIM_ERROR = {"500000007", "订单认领失败"};
	public static String[] TASK_USER_NOT_PERMISSION_LEASE = {"500000008", "您没有权限操作退租办理，请联系上一级负责人"};
	// 表操作
	public static String[] FIN_TRADE_RECORD_INSERT_ERROR = {"40000001", "插入fin_trade_record表失败"};
	// 分组操作
	public static String[] SYS_GROUP_DELETE_ERROR = {"30000001", "分组删除失败"};
	public static String[] SYS_POWER_DELETE_ERROR = {"30000002", "权限删除失败"};
	public static String[] SYS_POWER_ADD_ERROR = {"30000003", "权限添加失败"};
	public static String[] SYS_ROLE_DELETE_ERROR = {"30000004", "角色删除失败"};
	public static String[] SYS_ROLE_POWER_ASSIGN_ERROR = {"30000005", "角色权限分配失败"};
	public static String[] SYS_SMS_SEND_ERROR = {"30000006", "短信发送失败"};
	// file exception
	public static String[] FILE_TYPE_ERROR = {"30000002", "文件类型错误"};
	public static String[] FILE_STATION_IMPORT_ERROR = {"30000003", "工位导入失败"};
	public static String[] FILE_IMPORT_ERROR = {"30000004", "导入失败"};
	
	public static String[] AUTH_NO_LOGIN_ERROR = {"00000001", "未登录无法操作"};
	
	public static String[] AUTH_HAS_NO_POWER = {"00000001", "该用户无权进行此操作"};
}

