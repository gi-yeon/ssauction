"use strict";(self["webpackChunkssauction_front"]=self["webpackChunkssauction_front"]||[]).push([[418],{5418:function(t,e,a){a.r(e),a.d(e,{default:function(){return L}});var l=a(3396),r=a(9242),s=a(7139);const n={class:"table"},o=(0,l._)("h1",null,"BoardList",-1),u={class:"register"},i=(0,l._)("option",{disabled:"",selected:"",value:""},"-선택-",-1),c=(0,l._)("option",{value:"title"},"제목",-1),d=(0,l._)("option",{value:"userNickname"},"작성자",-1),g=[i,c,d],b=(0,l._)("button",null,"글쓰기",-1),p={class:"boardtable"},h=(0,l._)("colgroup",null,[(0,l._)("col",{style:{width:"10%"}}),(0,l._)("col",{style:{width:"50%"}}),(0,l._)("col",{style:{width:"20%"}}),(0,l._)("col",{style:{width:"20%"}})],-1),_=(0,l._)("thead",null,[(0,l._)("tr",null,[(0,l._)("th",null,"글번호"),(0,l._)("th",null,"제목"),(0,l._)("th",null,"작성자"),(0,l._)("th",null,"작성일")])],-1),m={class:"btnGroup"},w=["onClick"];function P(t,e,a,i,c,d){const P=(0,l.up)("router-link"),f=(0,l.up)("board-list-item");return(0,l.wg)(),(0,l.iD)("div",n,[o,(0,l._)("div",u,[(0,l._)("div",null,[(0,l.wy)((0,l._)("select",{"onUpdate:modelValue":e[0]||(e[0]=t=>i.state.searchType=t)},g,512),[[r.bM,i.state.searchType]]),(0,l.wy)((0,l._)("input",{type:"text",name:"search",id:"search","onUpdate:modelValue":e[1]||(e[1]=t=>i.state.search=t)},null,512),[[r.nr,i.state.search]]),(0,l._)("button",{onClick:e[2]||(e[2]=t=>i.getArticles(0))},"검색")]),(0,l.Wm)(P,{to:"regist"},{default:(0,l.w5)((()=>[b])),_:1})]),(0,l._)("table",p,[h,_,(0,l._)("tbody",null,[((0,l.wg)(!0),(0,l.iD)(l.HY,null,(0,l.Ko)(i.state.articles,((t,e)=>((0,l.wg)(),(0,l.j4)(f,(0,l.dG)({key:e},t),null,16)))),128))])]),(0,l._)("div",m,[(0,l._)("button",{onClick:e[3]||(e[3]=t=>i.getArticles(i.state.startPage-10))},"〈〈"),((0,l.wg)(!0),(0,l.iD)(l.HY,null,(0,l.Ko)(i.state.pages,((t,e)=>((0,l.wg)(),(0,l.iD)("span",{key:e},[(0,l._)("button",{onClick:e=>i.getArticles(t-1)},(0,s.zw)(t),9,w)])))),128)),(0,l._)("button",{onClick:e[4]||(e[4]=t=>i.getArticles(i.state.totalPages+10))},"〉〉")])])}const f={class:"up"};function y(t,e,a,r,n,o){return(0,l.wg)(),(0,l.iD)("tr",f,[(0,l._)("td",null,(0,s.zw)(a.boardNo),1),(0,l._)("td",{onClick:e[0]||(e[0]=t=>r.boardDetail())},(0,s.zw)(a.boardTitle),1),(0,l._)("td",null,(0,s.zw)(a.userNickname),1),(0,l._)("td",null,(0,s.zw)(a.boardRegDate),1)])}var k=a(678),N={name:"BoardListItem",props:{boardNo:Number,boardTitle:String,boardRegDate:String,boardReply:Number,boardType:String,userNickname:String,userNo:Number},setup(t){const e=(0,k.tv)(),a=()=>{e.push({name:"boardDetail",params:{id:t.boardNo}})};return{boardDetail:a}}},v=a(89);const D=(0,v.Z)(N,[["render",y]]);var T=D,C=a(8042),z=a(4870),B={name:"BoardList",components:{BoardListItem:T},setup(){const t=(0,z.qj)({startPage:Number,endPage:Number,articles:[],currentPage:Number,totalPages:Number,numberOfElements:Number,pages:[],first:Boolean,last:Boolean,searchType:"",search:""}),e=e=>{a(e,10,t.searchType,t.search)},a=(e,a,l,r)=>{e<0?alert("첫 페이지 리스트 입니다"):e>=t.totalPages?alert("마지막 페이지 리스트 입니다"):C.Z.get("http://localhost:8080/board",{params:{page:e,size:a,searchType:l,search:r}}).then((e=>{let{data:a}=e;console.log(a.list),t.currentPage=a.list.number,t.totalPages=a.list.totalPages,t.numberOfElements=a.list.numberOfElements,t.startPage=10*Math.floor((1+t.currentPage)/10)+1,t.endPage=10*Math.floor((1+t.currentPage)/10)+10>t.totalPages?t.totalPages:10*Math.floor((1+t.currentPage)/10)+10,t.pages.length=0;for(let l=t.startPage;l<=t.endPage;l++)t.pages.push(l);t.articles=a.list.content}))};return a(0,10),t.searchType="",{state:t,getArticles:e}}};const A=(0,v.Z)(B,[["render",P]]);var L=A}}]);
//# sourceMappingURL=418.4e3fb555.js.map