// 댓글 작성
function saveReply() {

    const replyContent = document.getElementById("replyContent").value;
    const boardId = document.getElementById("boardId").value;
    const memberId = document.getElementById("memberId").value;
    const replyWriter = document.getElementById("replyWriter").value;

    // const data={
    //     boardId:boardId,
    //     memberId:memberId,
    //     replyWriter:replyWriter,
    //     replyContent:replyContent
    // }
    if (replyContent.trim() === "") {
        alert("댓글을 입력하세요");
        return;
    }
    fetch("/reply/insert", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            replyContent: replyContent,
            boardId: boardId,
            memberId:memberId,
            replyWriter:replyWriter
        })
    })
        .then(res => {
            if (res.ok) {
                //성공시 다시 댓글 리스트 호출
                document.getElementById("replyContent").value = "";
                loadReplies();
            } else {
                alert("댓글 작성 실패");
            }
        });
}
// 댓글 삭제
function deleteReply(id) {

    //게시글 번호
    //회원번호

    if (!confirm("삭제하시겠습니까?")) return;

    fetch(`/reply/delete/${id}`, {
        method: "DELETE"
    })
        .then(res => {
            console.log(res)
            if (res.ok) {
                loadReplies();
            } else {
                alert("삭제 실패");
            }
        });

}
//댓글 수정
function updateReply(id,eTaget){
    // 타겟의 부모.부모.부모
    // button → li → ul → div.reply-item
    const replyItem= eTaget.parentElement.parentElement.parentElement;
    // 타겟의 부모.부모.부모 에서 .newContent  찾아서 value값 get
    const newContent = replyItem.querySelector(".newContent").value
    const boardId = document.getElementById("boardId").value;
    const memberId = document.getElementById("memberId").value;
    const replyWriter = document.getElementById("replyWriter").value;

    if(!confirm("수정하시겠습니까>")) return;

    if (newContent === null) return; // 취소
    if (newContent.trim() === "") {
        alert("댓글 내용을 입력하세요");
        return;
    }

    fetch(`/reply/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id:id,
            replyContent: newContent,
            boardId: boardId,
            memberId: memberId,
            replyWriter: replyWriter
        })
    })
        .then(res =>{
            console.log(res)
            if(res.ok){
                loadReplies();
            }else {
                alert("수정 실패");
            }
        });
}



// 댓글 목록
function loadReplies() {

    const boardId = document.getElementById("boardId").value;

    fetch(`/reply/list/${boardId}`)
        .then(res => res.json())
        .then(data => {
            console.log(data)
            let html = "";
            if (data.length === 0) {
                html = "<p>댓글이 없습니다.</p>";
            } else {
                data.forEach(reply => {
                    console.log(data)
                    html += `
                     <div class="reply-item">
                        <ul>
                             <li>
                             <input type="text" name="newContent" class="newContent" value="${reply.replyContent}">
                            </li>
                        </ul>
                            <small>작성자: ${reply.replyWriter}</small>
                             <button onclick="deleteReply(${reply.id})">삭제</button>
                             <button onclick="updateReply(${reply.id},this)">수정</button>
                         </div>
                    `;
                });
            }
            document.getElementById("replyList").innerHTML = html;
        });

}

//즉시 실행 함수
(()=>{
    loadReplies();
})();