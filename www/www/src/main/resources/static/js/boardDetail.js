console.log("board detail js");
document.getElementById('listBtn').addEventListener('click',()=>{
    location.href="/board/list"
})

document.getElementById('delBtn').addEventListener('click',()=>{
    document.getElementById('delForm').submit();
})

document.getElementById('modBtn').addEventListener('click',()=>{
    document.getElementById('title').readOnly=false;
    document.getElementById('content').readOnly=false;

    let modBtn=document.createElement('button');
    //<button><button>
    modBtn.setAttribute('type','submit');
    modBtn.classList.add('btn','btn-outline-warning')
    modBtn.innerText="submit";
    //생성한 버튼 modForm 에추가
    document.getElementById('modForm').appendChild(modBtn);
    
    //modBtn, delBtn 임시 삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();


})
