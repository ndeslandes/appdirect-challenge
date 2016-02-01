INSERT INTO subscription(id, company_name, edition, status, market_place_base_url)
                 VALUES (1, 'Jacefoil inc', 'FREE', 'INITIALIZED', 'https://example.org');
INSERT INTO user_account(id, openid, firstname, lastname, email, subscription_id)
                 VALUES (1, 'https://example.org/openid/id/openID', 'Nicolas', 'Deslandes', 'deslandes.nicolas@gmail.com', 1);