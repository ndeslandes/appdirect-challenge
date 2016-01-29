$.ajax({
type: 'POST',
url: '/api/users',
dataType: 'json',
success: function(response){
    var name = response;
    var yourTableHTML = "";
        jQuery.each(name, function(i,data) {
            $('#userTable').append('<tr><td>' + data.accountId + '</td><td>' + data.creatorFirstName + '</td><td>' + data.creatorLastName + '</td><td>' + data.edition + '</td></tr>');
        });
}
});