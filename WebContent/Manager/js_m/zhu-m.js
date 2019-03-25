
$(function(){
	/*跳转*/
	$(".notice_tit #tab_t li").click(function(){
		var index = $(this).index()
		$(this).addClass('select').siblings().removeClass('select');
		$(this).parent().parent().siblings().children().eq(index).show().siblings().hide();
	});	
	/*柱状图*/
var unexamine = document.getElementById('unexamine').value;
var pass = document.getElementById('pass').value;
var notPass = document.getElementById('notPass').value;
var frozen = document.getElementById('frozen').value;

unexamine=parseInt(unexamine);
pass=parseInt(pass);
notPass=parseInt(notPass);
frozen=parseInt(frozen);
//创建实例
var chart = echarts.init(document.getElementById('containerz'));
var option ={
		
		color:['#3398DB'],//颜色
		//提示框
		tooltip:{
			trigger:'axis',//坐标轴触发
			//坐标轴指示器配置
			axisPointer:{
				type:'shadow'//阴影指示器
			}
		},
		//绘制网格
		grid:{
			left:'3%',//左侧距离
			right:'4%',//右侧距离
			bottom:'3%',//下侧距离
			containLabel:true//grid区是否包含刻度
		},
		legend:{
			data:['统计']
		},
		//x轴
		xAxis:{
			data:['通过','未通过','未审核','冻结']//类目数据
		},
		yAxis:{
			
		},
		//系列列表
		series:[{
			name:'统计',//系列名
			type:'bar',//柱状
			barWidth:'40%',//柱条宽度
			data:[pass,notPass,unexamine,frozen]//数据
		}]
};
chart.setOption(option);

/*下面是利用Ajax传递数据的：
 * 1.后台编写的时候,可以选择$.get,$ajax来写，但是我觉得用getJSON比较好
 * 2.上面的数据都是利用数组形式编写，所以在后台请求回来的数据，
 * 最好就把上面的写死的数据删掉
 * 3.现在暂时注释下面代码
 * 4.下面饼状图同理
 * */
//$.getJSON(data.json).done(function(data){
//	chart.setOption({
//		xAxis:{
//			data:data.categories
//		},
//		series:[{
//			name:'统计',
//			data:data.data
//		}]
//	})
//})

//创建Echart实例
var mychart = echarts.init(document.getElementById('containerb'));
option = {
		//提示框
    tooltip : {
        trigger: 'item',//触发类型,数据项图形触发
        formatter: "{a} <br/>{b}:{c} ({d}%)"
    },
    //图例组件
    legend: {
        orient: 'vertical',//图例列表的布局朝向
        left: 'right',
        data: ['通过','未通过','未审核','冻结']
    },
    label:{
    	normal:{
    		position:'outside',
    	}
    },
    //图表
    series : [
        {
            name: '统计',//系列名称
            type: 'pie',//图类型
            radius : '55%',//饼圆半径，有两个属性值，第一个为内圆半径，第二个为外圆半径
            center: ['50%', '60%'],//饼圆圆心坐标，第一项横坐标，第二项纵坐标
            //数据数组
            data:[//name为数据项名称，value为值
                {value:pass, name:'通过'},
                {value:notPass, name:'未通过'},
                {value:unexamine, name:'未审核'},
                {value:frozen, name:'冻结'},
            ],
            itemStyle: {
	            	normal: {
		            		label: {
		            			show: true, //外部提示显示
		            			formatter: '{b}:{c} ({d}%)'//显示数据
		            				}
	            				},
	        //图形颜色
	                emphasis: {
	                    shadowBlur: 10,//图形阴影模糊
	                    shadowOffsetX: 0,//阴影水平偏移
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'//阴影颜色
	                			}
            },
          //标签视觉引导线
         	labelLine: {
         		label: {
         			show:true//显示引导线
         			}
            	},
            }
    ]
};
mychart.setOption(option);
})
//$.getJSON(data.json).done(function(data){
//        chart.setOption({
//            series:[{
//                name:'统计',
//                data:data.data
//            }]
//        })
//    })
//