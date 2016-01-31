$('.menu .item').tab();

$.ajax({
    type: 'GET',
    url: '/api/subscriptions',
    dataType: 'json',
    success: function(subscriptions){
        jQuery.each(subscriptions, function(i, subscription) {
            var usersTable = '';
            jQuery.each(subscription.users, function(i, user) {
                if (usersTable != '') usersTable += '<br>';
                usersTable += user.firstname + " " + user.lastname + "<a href='/login/openid?openid_identifier=" + user.openId + "'>login</href>";
            });
            $('#subscriptionTable').append('<tr><td>' + subscription.id + '</td><td>' + subscription.companyName + '</td><td>' + subscription.edition + '</td><td>' + $.trim(subscription.status) + '</td><td>' + usersTable + '</td></tr>');
        });
    }
});