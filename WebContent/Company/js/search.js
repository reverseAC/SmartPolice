  var DataTableHTML = $("#blocks").html();//加载网页的时候先把表格的搜索内容保存起来，一会儿搜索结果将填充这个表格。  
    function search(){  
        var searchStr = document.getElementById("search").value;//取出搜索框的内容  
        $("#blocks").html(DataTableHTML);//先将加载网页时的表格内容填充表格，在进行搜索，否则多次搜索之后，就是在搜索结果中再搜索了。  
        var DataTableBody = document.getElementById("blocks").tBodies[0];//取出tbody的内容  
        var DataRowsArr = DataTableBody.rows;//把tbody的每一行取出来形成一个数组，jQuery不知道怎么写，这里用javascript实现。  
        var MyArr = new Array();//建立一个空数组，用来存含有搜索内容的行节点  
        var MyArrRowsCount = 0;//空数组的索引值  
        for (var i = 0; i < DataRowsArr.length; i++) {//对表格的每一行进行遍历  
            var isSearchStrExisted = false;  
            for (var j = 0; j < DataRowsArr[i].cells.length; j++) {//对每一行中的每一个单元格进行遍历  
                var cellInnerHTML = DataRowsArr[i].cells[j].innerHTML + "";//取出单元格的内容  
                if (cellInnerHTML.indexOf(searchStr) > -1) {//如果其含有用户输入的搜索的内容，  
                    MyArr[MyArrRowsCount] = DataRowsArr[i];//则加入的空数组中  
                    MyArrRowsCount++;//索引+1  
                }  
            }  
        }  
        $(DataTableBody).html("");//利用jQuery清空原来的tbody，这里用Javascript实现不了……  
        var frag = document.createDocumentFragment();//建立一个文件碎片  
        for (var i = 0; i < MyArr.length; i++) {  
            frag.appendChild(MyArr[i]);//将匹配搜索结果的行全部添加到文件碎片中    
        }  
        DataTableBody.appendChild(frag);//再把这个文件碎片放上tbody  
    }  