/**
 * Created by Leo on 7/16/2017.
 */
function getFile() {
    $.ajax({
        url: '/upload',
        type: 'POST',
        cache: false,
        data: new FormData($('#uploadForm')[0]),
        processData: false,
        contentType: false
    }).done(function(res) {
    }).fail(function(res) {});
}
