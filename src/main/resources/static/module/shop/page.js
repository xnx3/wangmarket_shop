/**
 * 网市场分页相关的js模块
 * 作者：管雷鸣
 * 个人网站：www.guanleiming.com
 * 个人微信: xnx3com
 * 公司：潍坊雷鸣云网络科技有限公司
 * 公司官网：www.leimingyun.com
 */
var page = {
	page:{},	//记录显示最后一次分页传入的page数据
	template:null,	//id是page的div的innerHtml。当然如果用户的html里面没有这个id的话，那这里就是null
	upListTemplate:null, //id是upListLi的div的innerHtml，存的是上几页的跳转模板。当然如果用户的html里面没有这个id的话，那这里就是null
	nextListTemplate:null, //id是nextListLi的div的innerHtml，存的是下几页的跳转模板。当然如果用户的html里面没有这个id的话，那这里就是null
	upNextPageNumber:2,	//上几页、下几页，显示的数。如这里是3，则向上会显示3页
	/**
	 * 获取当前正在看的是第几页，也就是最近一次加载是加载的第几页。如果还没加载过，那也是返回1
	 */
	getCurrentPageNumber:function(){
		if(page.currentPageNumber == null){
			return 1;
		}
		return page.currentPageNumber;
	},
	/**
	 * 获取上一页的页码，如上一页是第二页，则返回 2
	 */
	getUpPageNumber:function(){
		if(this.page.haveUpPage){
			return this.page.currentPageNumber-1;
		}else{
			//没有上一页了，那这就是第一页
			return 1;
		}
	},
	/**
	 * 获取下一页的页码，如下一页是第二页，则返回 2
	 */
	getNextPageNumber:function(){
		if(this.page.haveNextPage){
			return this.page.currentPageNumber+1;
		}else{
			//没有下一页了，这已经是最后一页了
			return this.page.lastPageNumber;
		}
	},
	/**
	 * 将分页模版进行替换，加入动态数据
	 * @param page 实际的分页数据，传入如：  {limitStart: 15, allRecordNumber: 6, currentPageNumber: 2, everyNumber: 15, lastPageNumber: 1,…}
	 * @return 返回替换好的，已经将参数替换好的内容，可以拿来直接赋予 id="page" 这个div
	 */
	render:function(item){
		//先判断模版是否有，没有，那就是第一次，再取一次模版
		if(this.template == null){
			//获取page的模版
			if(document.getElementById('page') != null){
				this.template = document.getElementById('page').innerHTML;
			}
			//获取 上几页 的upList模板
			if(document.getElementById('upList') != null){
				this.upListTemplate = document.getElementById('upList').innerHTML;
			}
			//获取 下几页 的nextList模板
			if(document.getElementById('nextList') != null){
				this.nextListTemplate = document.getElementById('nextList').innerHTML;
			}
		}
		this.page = item;
		
		var html = this.template.replace(/\{allRecordNumber\}/g, item.allRecordNumber)
			.replace(/\{currentPageNumber\}/g, item.currentPageNumber)
			.replace(/\{lastPageNumber\}/g, item.lastPageNumber)
			.replace(/\{firstPage\}/g, item.firstPage)
			.replace(/\{upPage\}/g, item.upPage)
			.replace(/\{nextPage\}/g, item.nextPage)
			.replace(/\{lastPage\}/g, item.lastPage)
			.replace(/\{haveUpPage\}/g, item.haveUpPage)
			.replace(/\{haveNextPage\}/g, item.haveNextPage)
			.replace(/\{upList\}/g, item.upList)
			.replace(/\{nextList\}/g, item.nextList)
			.replace(/\{upPageNumber\}/g, item.upPageNumber)
			.replace(/\{nextPageNumber\}/g, item.nextPageNumber)
			;
		
		//是否是第一页
		if(item.currentPageNumber == 1){
			//当前是第一页，那么隐藏首页、上一页
			document.getElementById('firstPage').style.display = 'none';
		}else{
			//当前不是第一页，那么现实首页、上一页
			document.getElementById('firstPage').style.display = '';
		}
		//是否是最后一页
		if(item.lastPageNumber - item.currentPageNumber > 0){
			//当前不是最后一页
			document.getElementById('lastPage').style.display = '';
		}else{
			//当前是最后一页，那么不在显示下一页、尾页
			document.getElementById('lastPage').style.display = 'none';
		}
		
		
		
		if(document.getElementById("page") != null){
			document.getElementById("page").innerHTML = html;
		}
		//判断是否有上几页的翻页
		if(this.upListTemplate != null){
			//显示几页跳转链接
			var upListNumber = this.upNextPageNumber; 
			if(item.currentPageNumber < this.upNextPageNumber){
				upListNumber = item.currentPageNumber - 1; //页数不够，那么就只显示当前页面往前的几页
			}
			
			var upListHtml = '';
			for(var i=1; i<upListNumber+1; i++){
				var cnum = item.currentPageNumber-i;
				if(cnum > 0){
					upListHtml =  this.upListTemplate.replace(/\{href\}/g, 'javascript:list('+cnum+');')
					 								 .replace(/\{title\}/g, cnum+'')
					 				+ upListHtml;
				}
			}
			document.getElementById("upList").innerHTML = upListHtml;
		}
		//判断是否有下几页的翻页
		if(this.nextListTemplate != null){
			//显示几页跳转链接
			var nextListNumber = this.upNextPageNumber; 
			if(item.currentPageNumber + this.upNextPageNumber > item.lastPageNumber){
				nextListNumber = item.currentPageNumber + 1; //页数不够，那么就只显示当前页面往后的几页
			}
			
			var nextListHtml = '';
			for(var i=1; i<nextListNumber+1; i++){
				var cnum = item.currentPageNumber+i;
				if(cnum < item.lastPageNumber+1){
					nextListHtml =  nextListHtml + this.nextListTemplate.replace(/\{href\}/g, 'javascript:list('+cnum+');')
					 								 	 .replace(/\{title\}/g, cnum+'');
				}
			}
			document.getElementById("nextList").innerHTML = nextListHtml;
		}
	}
}

