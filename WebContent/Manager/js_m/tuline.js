function show(i) {
						
						var num = document.getElementsByClassName("num");
						var bodyshow = document
								.getElementById("body_show_" + i);
						var m;//for循环参数，使主页面隐藏
						for (m = 1; m <= num.length; m++) {
							document.getElementById("body_show_" + m).style.display = "none";
						}
						bodyshow.style.display = "block";
					}
 

var a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,i=0,j=0,k=0,l=0,m=0,n=0,o=0,p=0,q=0,r=0,s=0,t=0,u=0,v=0,w=0,x=0;
function chipan(sum){
	        var Z = new Chart(document.getElementById("chartjs-2"), {
	            //定义类型
	            type: "line",
	            //数据

	            data: {
	                //显示轴类别
	                labels: ["", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", "", "", ""],
	                //数据
	                datasets: [{
	                    // yAxisID:'sum',
	                     data: [a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x],
	                    //是否填充(线条下方区域填充)
	                    fill: true,
	                    //线条宽度
	                    borderWidth: 1,
	                    //线条颜色
	                    borderColor: "#ccc",
	                    //线下填充色，=fillColor      --》 Color
	                    backgroundColor: "rgba(220, 220, 220, 0.5)",
	                    //线张力，0为直线
	                    lineTension: 0,
	                    //点的大小

	                    pointRadius: 0,
	                }]
	            },
	            
	            options: {
	            	fullWidth:true,
	                // 显示轴线、以及刻度，默认为true
	                showScale: false,
	                //说明设置//标签设置
	                legend: {
	                    //是否显示，             --》 Boolean
	                    display: false,
	                    labels: {
	                        boxWidth: 20, //着色盒子的宽度
	                    }
	                },
	                bezierCurve: false,

	                //动画设置
	                animation: {
	                    //动画时间
	                    duration: 0, // general animation time
	                },
	                //互动或鼠标悬停设置
	                events: [''],
	                //onHover: null,//不触发悬浮事件
	                //未知
	               
	                scales: {
	                    yAxes: [{
	                        ticks: {
	                        	 
	                        	 show: false,
	                            //从零开始
	                            beginAtZero: true,
	                            //限定值
	                            min: 0,
	                            max: 100,
	                        },

	                    }]
	                },
	            }
	        });
	    	 //。。。。。。。。。。变量赋值
	        a=b;
	        b=c;
	        c=d;
	        d=e;
	        e=f;
	        f=g;
	        g=h;
	        h=i;
	        i=j;
	        j=k;
	        k=l;
	        l=m;
	        m=n;
	        n=o;
	        o=p;
	        p=q;
	        q=r;
	        r=s;
	        s=t;
	        t=u;
	        u=v;
	        v=w;
	        w=x;
	        x=sum;
	    }
var A=0,B=0,C=0,D=0,E=0,F=0,G=0,H=0,I=0,J=0,K=0,L=0,M=0,N=0,O=0,P=0,Q=0,R=0,S=0,T=0,U=0,V=0,W=0,X=0;
      function neicun(sum){
    	 
	        var Z = new Chart(document.getElementById("chartjs-1"), {
	            //定义类型
	            type: "line",
	            //数据

	            data: {
	                //显示轴类别
	                labels: ["", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
	                    "", "", "", ""
	                ],
	                //数据
	                datasets: [{
	                    // yAxisID:'sum',
	                     data: [A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X],
	                    //是否填充(线条下方区域填充)
	                    fill: true,
	                    //线条宽度
	                    borderWidth: 1,
	                    //线条颜色
	                    borderColor: "#ccc",
	                    //线下填充色，=fillColor      --》 Color
	                    backgroundColor: "rgba(220, 220, 220, 0.5)",
	                    //线张力，0为直线
	                    lineTension: 0,
	                    //点的大小

	                    pointRadius: 0,
	                }]
	            },
	            
	            options: {
	            	fullWidth:true,
	                // 显示轴线、以及刻度，默认为true
	                showScale: false,
	                //说明设置//标签设置
	                legend: {
	                    //是否显示，             --》 Boolean
	                    display: false,
	                    labels: {
	                        boxWidth: 20, //着色盒子的宽度
	                    }
	                },
	                bezierCurve: false,

	                //动画设置
	                animation: {
	                    //动画时间
	                    duration: 0, // general animation time
	                },
	                //互动或鼠标悬停设置
	                events: [''],
	                //onHover: null,//不触发悬浮事件
	                //未知
	               
	                scales: {
	                    yAxes: [{
	                        ticks: {
	                        	 
	                        	 show: false,
	                            //从零开始
	                            beginAtZero: true,
	                            //限定值
	                            min: 0,
	                            max: 100,
	                        },

	                    }]
	                },
	            }
	        });
	     
	    	
	    	 //。。。。。。。。。。变量赋值
	        A=B;
	        B=C;
	        C=D;
	        D=E;
	        E=F;
	        F=G;
	        G=H;
	        H=I;
	        I=J;
	        J=K;
	        K=L;
	        L=M;
	        M=N;
	        N=O;
	        O=P;
	        P=Q;
	        Q=R;
	        R=S;
	        S=T;
	        T=U;
	        U=V;
	        V=W;
	        W=X;
	        X=sum;
      }
      var aa=0,bb=0,cc=0,dd=0,ee=0,ff=0,gg=0,hh=0,ii=0,jj=0,kk=0,ll=0,
      mm=0,nn=0,oo=0,pp=0,qq=0,rr=0,ss=0,tt=0,uu=0,vv=0,ww=0,xx=0;
      function inter(sum){
	      for(var y=3;y<=4;y++)  {
    	  var Z = new Chart(document.getElementById("chartjs-"+y), {
	            //定义类型
	            type: "line",
	            //数据

	            data: {
	                //显示轴类别
	                labels: ["", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
	                    "", "", "", ""
	                ],
	                //数据
	                datasets: [{
	                    // yAxisID:'sum',
	                     data: [aa,bb,cc,dd,ee,ff,gg,hh,ii,jj,kk,ll,mm,nn,oo,pp,qq,rr,ss,tt,uu,vv,ww,xx],
	                    //是否填充(线条下方区域填充)
	                    fill: true,
	                    //线条宽度
	                    borderWidth: 1,
	                    //线条颜色
	                    borderColor: "#ccc",
	                    //线下填充色，=fillColor      --》 Color
	                    backgroundColor: "rgba(220, 220, 220, 0.5)",
	                    //线张力，0为直线
	                    lineTension: 0,
	                    //点的大小

	                    pointRadius: 0,
	                }]
	            },
	            
	            options: {
	            	fullWidth:true,
	                // 显示轴线、以及刻度，默认为true
	                showScale: false,
	                //说明设置//标签设置
	                legend: {
	                    //是否显示，             --》 Boolean
	                    display: false,
	                    labels: {
	                        boxWidth: 20, //着色盒子的宽度
	                    }
	                },
	                bezierCurve: false,

	                //动画设置
	                animation: {
	                    //动画时间
	                    duration: 0, // general animation time
	                },
	                //互动或鼠标悬停设置
	                events: [''],
	                //onHover: null,//不触发悬浮事件
	                //未知
	               
	                scales: {
	                    yAxes: [{
	                        ticks: {
	                        	 
	                        	 show: false,
	                            //从零开始
	                            beginAtZero: true,
	                            //限定值
	                            min: 0,
	                            max: 100,
	                        },

	                    }]
	                },
	            }
	        });
      }
	    	
	    	 //。。。。。。。。。。变量赋值
	        aa=bb;
	        bb=cc;
	        cc=dd;
	        dd=ee;
	        ee=ff;
	        ff=gg;
	       gg=hh;
	        hh=ii;
	        ii=jj;
	        jj=kk;
	        kk=ll;
	        ll=mm;
	        mm=nn;
	        nn=oo;
	        oo=pp;
	        pp=qq;
	        qq=rr;
	        rr=ss;
	        ss=tt;
	        tt=uu;
	        uu=vv;
	        vv=ww;
	        ww=xx;
	        xx=sum;
      }
      var AA=0,BB=0,CC=0,DD=0,EE=0,FF=0,GG=0,HH=0,II=0,JJ=0,KK=0,LL=0,MM=0,NN=0,OO=0,PP=0,QQ=0,RR=0,SS=0,TT=0,UU=0,VV=0,WW=0,XX=0;
      function cpu(sum){
    	  
	        var Z = new Chart(document.getElementById("chartjs-0"), {
	            //定义类型
	            type: "line",
	            //数据

	            data: {
	                //显示轴类别
	                labels: ["", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
	                    "", "", "", ""
	                ],
	                //数据
	                datasets: [{
	                    // yAxisID:'sum',
	                     data: [AA,BB,CC,DD,EE,FF,GG,HH,II,JJ,KK,LL,MM,NN,OO,PP,QQ,RR,SS,TT,UU,VV,WW,XX],
	                    //是否填充(线条下方区域填充)
	                    fill: true,
	                    //线条宽度
	                    borderWidth: 1,
	                    //线条颜色
	                    borderColor: "#ccc",
	                    //线下填充色，=fillColor      --》 Color
	                    backgroundColor: "rgba(220, 220, 220, 0.5)",
	                    //线张力，0为直线
	                    lineTension: 0,
	                    //点的大小

	                    pointRadius: 0,
	                }]
	            },
	            
	            options: {
	            	fullWidth:true,
	                // 显示轴线、以及刻度，默认为true
	                showScale: false,
	                //说明设置//标签设置
	                legend: {
	                    //是否显示，             --》 Boolean
	                    display: false,
	                    labels: {
	                        boxWidth: 20, //着色盒子的宽度
	                    }
	                },
	                bezierCurve: false,

	                //动画设置
	                animation: {
	                    //动画时间
	                    duration: 0, // general animation time
	                },
	                //互动或鼠标悬停设置
	                events: [''],
	                //onHover: null,//不触发悬浮事件
	                //未知
	               
	                scales: {
	                    yAxes: [{
	                        ticks: {
	                        	 
	                        	 show: false,
	                            //从零开始
	                            beginAtZero: true,
	                            //限定值
	                            min: 0,
	                            max: 100,
	                        },

	                    }]
	                },
	            }
	        });
	     
	    	
	    	 //。。。。。。。。。。变量赋值
	        AA=BB;
	        BB=CC;
	        CC=DD;
	        DD=EE;
	        EE=FF;
	        FF=GG;
	        GG=HH;
	        HH=II;
	        II=JJ;
	        JJ=KK;
	        KK=LL;
	        LL=MM;
	        MM=NN;
	        NN=OO;
	        OO=PP;
	        PP=QQ;
	        QQ=RR;
	        RR=SS;
	        SS=TT;
	        TT=UU;
	        UU=VV;
	        VV=WW;
	        WW=XX;
	        XX=sum;
      }