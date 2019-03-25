$(function() {

	// 1.初始化Table
	var oTable = new TableInit();
	oTable.Init();

	// 2.初始化Button的点击事件
	var oButtonInit = new ButtonInit();
	oButtonInit.Init();

});

(function(i, s, o, g, r, a, m) {
	i['GoogleAnalyticsObject'] = r;
	i[r] = i[r] || function() {
		(i[r].q = i[r].q || []).push(arguments)
	}, i[r].l = 1 * new Date();
	a = s.createElement(o), m = s.getElementsByTagName(o)[0];
	a.async = 1;
	a.src = g;
	m.parentNode.insertBefore(a, m)
})(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');
ga('create', 'UA-36708951-1', '');
ga('send', 'pageview');

var TableInit = function() {
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function() {
		$('#blocks').bootstrapTable({
			url : 'comment.action', // 请求后台的URL（*）
			method : 'post', // 请求方式（*）post/get
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : true,
			showColumns : false, // 是否显示所有的列
			showRefresh : false, // 是否显示刷新按钮
			minimumCountColumns :2, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			height : "",// 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			locale: "zh-CN", //中文支持
			columns : [ {
				checkbox : true
			}, {
				field : 'commentid',
				title : '序号'
			}, {
				field : 'title',
				title : '反馈主题'
			}, {
				field : 'date',
				title : '反馈时间'
			} ,
			{
				field : 'type',
				title : '反馈类型'
			} ,
			{
				field : 'userid',
				title : '反馈人'
			} ,
			{
				field : 'handle',
				title : '状态'
			} ,
			{
				field : 'action',
				title : '操作',
				events:operateEvents,
				formatter: //对本列数据做格式化
				function (value, row, index) {//赋予的参数
                    return [
                        '<a class="btn1" type="button" id="chakan">查看回复</a>',
                        '<a class="btn2" type="button" id="deletee"> 删除</a>'
                    ].join('');
                }
			} 
			]
		});
	};
	window.operateEvents={
			
			"click #chakan":function(e,value,row,index){
				var m=$(this).parent().parent().find("td").eq(1).text();

				$(this).attr("href","checkComment.action?id="+m);
			},

			"click #deletee":function(e,value,row,index){
				var m=$(this).parent().parent().find("td").eq(1).text();
				
				$(this).attr("href","removeComment.action?id="+m);
			}
	}
	// 得到查询的参数
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
		// departmentname : $("#txt_search_departmentname").val(),
		// statu : $("#txt_search_statu").val()
		};
		return temp;
	};
	return oTableInit;
};

var ButtonInit = function() {
	var oInit = new Object();
	var postdata = {};

	oInit.Init = function() {
	};

	return oInit;
};

$(document).ready(function(){
	$("#delete").click(function(){
		var id=new Array();
		var j=0;
        for(var i=1;i<$("#tb_departments").find("input[type=checkbox]").length;i++)
    if($("#tb_departments").find("input[type=checkbox]").eq(i).is(':checked')){
        id[j]=$("#tb_departments").find("input[type=checkbox]").eq(i).parent().parent().find("td").eq(1).html();
    j++;
    }
        $("#delete").attr("href","removeComments.action?id="+id);
    })
    
})