document.querySelector('.delete').onclick = function (){
    if(!confirm('정말 삭제할까요?')) return;
    document.getElementById('deleteForm').submit();
}