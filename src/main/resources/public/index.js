$.ajax({
type: 'POST',
url: '/api/users',
dataType: 'json',
success: function(response){
    var name = response;
    var yourTableHTML = "";
        jQuery.each(name, function(i,data) {
            $('#userTable').append('<tr><td>' + data.id + '</td><td>' + data.firstname + '</td><td>' + data.lastname + '</td><td>' + data.email + '</td><td>' + data.language + '</td></tr>');
        });
}
});