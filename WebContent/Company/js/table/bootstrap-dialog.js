/**
 * Created by guo on 2017/3/25.
 */
$("#addBtn").on("click", function() {
    $.FORM.showFormDialog({  //��ʾ��Ҫ�ύ�ı���
        title: "���VIP����", //����
        postUrl: "", //�����ύ�Ľӿ�
        templateUrl: 'dialogform.html',  //form�����ڵ�jspҳ�棬��mysql���ֶ���ӡ�
        formId: "#vipForm",  // ��Ҫ�ύ��form����id
        postType: "multipart",  // �ύ�������ͣ����̨@requestBody����һ�¡�
        data: {  //�Զ����ϴ��Ĳ���
            pid: 0,
            pname: "--",
            level: 0
        },
        onPostSuccess: function() {
            $("#table").bootstrapTable("refresh");  //����ɹ�ˢ�±�񣬼�������
        }
    });
});
