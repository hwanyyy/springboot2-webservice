// 굳이 index를 추가한 이유: 나중에 index.mustache에 a.js가 추가되어 a.js만의 init과 sava function이 있으면 브라우저의 스코프는 공용공간으로 쓰이기 때문에 나중에 로딩된 init, save가 덮어쓰게 됨
// 이런 문제를 피하기 위해 index.js만의 유효범위(scope)를 만듦
var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {      //btn-update란 id를 가진 HTML element에 click event가 발생할 때 update function을 실행하도록 이벤트를 등록
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save: function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';         // 글 등록 성공시 되돌아감
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function(){
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',    //PostsApiController에 있는 API에서 이미 @PUTMapping으로 선언 함
            url: '/api/v1/posts/'+id,   //어느 게시글을 수정할 지 URL path로 구분하기 위해 id를 추가
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(() => {
            alert('글이 수정되었습니다.');
            window.location.href='/';
        }).fail((error) => {
            alert(JSON.stringify(error));
        });
    },
    delete: function(){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(() => {
            alert('글이 삭제되었습니다.');
            window.location.href='/';
        }).fail((error) => {
            alert(JSON.stringify(error));
        });
    }
};

main.init();