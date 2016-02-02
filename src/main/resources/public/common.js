$('.menu .item').tab();

$.ajax({
    type: 'GET',
    url: '/api/user/current',
    dataType: 'json',
    success: function(user){
        $('#currentUser').html(user.firstname + " " + user.lastname);
        $('#logButton').attr('href', '/logout').html("Logout");
    },
    error: function (xhr, ajaxOptions, thrownError) {
        $('#currentUser').html("Anonymous");
        $('#logButton').attr('href', '/login/openid?openid_identifier=https://jacefoil-test.byappdirect.com/openid/id').html("Login");
    }
});
