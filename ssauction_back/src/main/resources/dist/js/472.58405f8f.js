"use strict";(self["webpackChunkssauction_front"]=self["webpackChunkssauction_front"]||[]).push([[472],{4472:function(t,o,r){r.r(o),r.d(o,{default:function(){return N}});var e=r(3396),a=r(9242);const n=(0,e._)("div",null,null,-1),d=(0,e._)("button",null,"목록",-1);function i(t,o,r,i,l,u){const b=(0,e.up)("router-link");return(0,e.wg)(),(0,e.iD)("div",null,[(0,e.wy)((0,e._)("input",{type:"text",name:"title",id:"title","onUpdate:modelValue":o[0]||(o[0]=t=>i.state.boardTitle=t)},null,512),[[a.nr,i.state.boardTitle]]),n,(0,e.wy)((0,e._)("textarea",{name:"content",id:"content",cols:"30",rows:"10","onUpdate:modelValue":o[1]||(o[1]=t=>i.state.boardContent=t)},"\r\n\r\n        ",512),[[a.nr,i.state.boardContent]]),(0,e._)("div",null,[(0,e._)("button",{onClick:o[2]||(o[2]=function(){return i.ModifyArticle&&i.ModifyArticle(...arguments)})},"글수정하기"),(0,e.Wm)(b,{to:"/board"},{default:(0,e.w5)((()=>[d])),_:1})])])}var l=r(8042),u=r(4870),b=r(678),s={name:"BoardModify",setup(){const t=(0,b.tv)(),o=(0,b.yj)(),r=(0,u.qj)({boardNo:Number,boardTitle:String,boardContent:String,userNo:Number,userNickname:String}),e=()=>{let o={boardNo:r.boardNo,boardTitle:r.boardTitle,boardContent:r.boardContent};l.Z.put("http://localhost:8080/board",JSON.stringify(o)).then((()=>{alert("수정이 완료되었습니다.")})),t.push({name:"boardDetail",params:{id:r.boardNo}})};return l.Z.get("http://localhost:8080/board/detail",{params:{boardNo:o.params.id}}).then((t=>{let{data:o}=t;r.boardNo=o.board.boardNo,r.boardTitle=o.board.boardTitle,r.boardContent=o.board.boardContent,r.userNo=o.board.userNo,r.userNickname=o.board.userNickname})),{state:r,ModifyArticle:e}}},c=r(89);const p=(0,c.Z)(s,[["render",i]]);var N=p}}]);
//# sourceMappingURL=472.58405f8f.js.map