
document.getElementById('cmtAddBtn').addEventListener('click',(e)=>{
    const cmtText=document.getElementById('cmtText').value;
    const cmtWriter=document.getElementById('cmtWriter').innerHTML;

    if(cmtText==null||cmtText==''){
        alert("댓글을 입력해주세요");
        document.getAnimations('cmtText').focus();
        return false;
    }else{
        let cmtDate={
            bno:bnoVal,
            writer:cmtWriter,
            content:cmtText
        
        };
        console.log(cmtDate);
        postCommentServer(cmtDate).then(result=>{
            if(result==1){
                alert("댓글등록 성공");
                document.getElementById('cmtText').value="";
            }
            spreadCommentList(bnoVal);
        })
    }
})
//페이징 처리를 하여 ,한페이지(더보기)에 5개의 댓글을 출력
//전체 게시글 수에 따른 페이지 수
function spreadCommentList(bno,page=1){
    getCommentListFromServer(bno,page).then(result=>{
        console.log(result);
        //댓글 뿌리기
        const ul=document.getElementById('cmtListArea');
        if(result.cmtList.length>0){
            if(page==1){
                //page 1이면 뿌려줘 댓글 내용 지우기
                ul.innerHTML=''; //원래 있던 html 값 지우기
            }

            for(let cvo of result.cmtList){
                let li=`<li class="list-group-item" data-cno="${cvo.cno}">`;
                li+=`<div class="input-group mb-3">no.${cvo.cno}:`;
                li+=`<div class="fw-bold">${cvo.writer}</div>`
                li+=`${cvo.content}`;
                li+=`</div>`;
                li+=`<span class="badge rounded-pill text-bg-warning">${cvo.modAt}</span>`;
                //수정삭제버튼
                li+=`<button type="button" class="btn btn-outline-warning btn-sm mod " data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`
                li+=`<button type="button" data-cno="${cvo.cno}" class="btn btn-outline-danger btn-sm del">삭제</button>`
                li+=`</li>`
                ul.innerHTML+=li;

            }
            //더보기 버튼
            //PAGE 처리
            let moreBtn=document.getElementById('moreBtn');
            console.log(moreBtn);
            //moreBtn 표시되는 조건
            //pgvo.pageNo=1 /realEndPage=3
            //realEndPage 보다 현재 내페이지가 작으면 표시
            if(result.pgvo.pageNo<result.endPage){
                //style="visibility:hidden"=숨김 visibility:visible=표시
                moreBtn.style.visibility='visible'; //버튼 표시
                moreBtn.dataset.page=page+1;
            }else{
                moreBtn.style.visibility='hidden';//숨김
            }

                
        }else{
            let li=`<li class="list-group-item">댓글이 없습니다</li>`
            ul.innerHTML=li;
        }
    })
}



async function postCommentServer(cmtDate){
    try {
        const url="/comment/post";
        const config={
            method:"post",
            headers:{
                "content-type":"application/json; charset=utf-8"
            },
            body:JSON.stringify(cmtDate)
        };
        const resp=await fetch(url, config);
        const result=await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }

}
async function getCommentListFromServer(bno,page){
    try {
        const resp=await fetch("/comment/list/"+bno+"/"+page)
        const result=await resp.json(); //객체
        return result;
    } catch (error) {
        console.log(error);
    }
};

document.addEventListener('click',(e)=>{
    if(e.target.id=='moreBtn'){
        let page=parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal,page);
    }
    //수정시 모달창을 통해 댓글 입력받기
    else if(e.target.classList.contains('mod')){
        //closest 제일 가까운 //내가 수정버튼을 누른 댓글의 li
        let li=e.target.closest('li');
        //writer 를 텅헤사 id="modWriter" 넣기
        let modWriter=li.querySelector(".fw-bold").innerText;
        document.getElementById('modWriter').innerText=modWriter;



        //nextSibling:한 부모 안에서 다음 형제를 찾기
        let cmtText=li.querySelector('.fw-bold').nextSibling;
        console.log(cmtText);
        document.getElementById('cmtTextMod').value=cmtText.nodeValue;

        //수정=>cno dataset으로 달기 cno,content
        document.getElementById('cmtModBtn').setAttribute("data-cno",li.dataset.cno)
    }else if(e.target.id=='cmtModBtn'){
        let cmtModata={
            cno:e.target.dataset.cno,
            content:document.getElementById('cmtTextMod').value
        };
        console.log(cmtModata);
        //비동기로 보내기
        updateCommentServer(cmtModata).then(result=>{
            if(result=='1'){
                alert("댓글수정")
                document.getElementById('cmtText').value="";
                spreadCommentList(bnoVal);
            }

        })

        }
        else if(e.target.classList.contains('del')){
            // let cnoVal=e.target.dataset.cno;
            let li=e.target.closest('li');
            let cnoVal=li.dataset.cno;
            console.log(cnoVal)
            //비동기로 삭제요청
            removerCommentServer(cnoVal).then(result=>{
                console.log(result)
                if(result=="1"){
                    alert("댓글삭제 성공");
                    spreadCommentList(bnoVal);
                    console.log(result);
                    console.log(bnoVal);
                }
            })
         
        }
    })
    

    
    async function updateCommentServer(cmtData){
        try {
            const url="/comment/modify"
            const config={
                method:"put",
                headers:{
                    'Content-type':'application/json; charset=utf-8'
                },
                body:JSON.stringify(cmtData)
            }
            const resp=await fetch(url,config)
            const result=await resp.text();
            return result;
        } catch (error) {
            console.log(error)
        }
        }
        async function removerCommentServer(cnoVal){
            try {
                const url="/comment/remove/"+cnoVal;
                const config={
                    method:"delete"
                }
                const resp=await fetch(url,config)
                const result=await resp.text();
                return result;
            } catch (error) {
                console.log(error)
            }
        }