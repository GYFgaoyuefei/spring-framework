--serv_client_details
INSERT INTO `serv_client_details` (`id`, `access_token_validity`, `additional_information`, `authorities`, `auto_approve_scopes`, `client_id`, `grant_types`, `redirect`, `refresh_token_validity`, `resource_ids`, `scope`, `secret`) VALUES (1, 20000, NULL, NULL, 'test', 'test', 'password,authorization_code', NULL, 20000, NULL, 'test', '123456');
INSERT INTO `serv_client_details` (`id`, `access_token_validity`, `additional_information`, `authorities`, `auto_approve_scopes`, `client_id`, `grant_types`, `redirect`, `refresh_token_validity`, `resource_ids`, `scope`, `secret`) VALUES (2, 20000, NULL, NULL, 'callback', 'callback', 'password,authorization_code', NULL, 20000, NULL, 'callback', '123456');