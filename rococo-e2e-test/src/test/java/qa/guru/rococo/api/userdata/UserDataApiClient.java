package qa.guru.rococo.api.userdata;

import qa.guru.rococo.api.ApiClient;
import qa.guru.rococo.model.UserJson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UserDataApiClient extends ApiClient {

    public UserDataApiClient() {
        super(CFG.userdataUrl());
    }

    private final UserdataApi api = retrofit.create(UserdataApi.class);

    @Nullable
    public UserJson getCurrentUser(@Nonnull String username) throws Exception {
        return api.currentUser(username)
                .execute()
                .body();
    }

    @Nullable
    public UserJson editUser(@Nonnull UserJson userJson) throws Exception {
        return api.editUser(userJson)
                .execute()
                .body();
    }
}
