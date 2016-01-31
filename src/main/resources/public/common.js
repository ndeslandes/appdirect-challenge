$('.menu .item').tab();

$.ajax({
    type: 'GET',
    url: '/api/user/current',
    dataType: 'json',
    success: function(user){
        $('#currentUser').html(user.firstname + " " + user.lastname).attr('title', user.email);
        $('#logButton').attr('href', '/logout').html("Logout");
    },
    error: function (xhr, ajaxOptions, thrownError) {
        $('#currentUser').html("Anonymous");
        $('#logButton').attr('href', '/login/openid?openid_identifier=https://www.appdirect.com/openid/id').html("Login");
    }
});
