/**
 * Created by guo on 2017/3/25.
 */
$("#addBtn").on("click", function() {
    $.FORM.showFormDialog({  //显示需要提交的表单。
        title: "添加VIP订单", //标题
        postUrl: "", //数据提交的接口
        templateUrl: 'dialogform.html',  //form表单所在的jsp页面，在mysql中手动添加。
        formId: "#vipForm",  // 需要提交的form表单的id
        postType: "multipart",  // 提交数据类型，与后台@requestBody保持一致。
        data: {  //自定义上传的参数
            pid: 0,
            pname: "--",
            level: 0
        },
        onPostSuccess: function() {
            $("#table").bootstrapTable("refresh");  //请求成功刷新表格，加载数据
        }
    });
});
