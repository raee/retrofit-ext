/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    /**
     * id : 30
     * description : android app
     * default_branch : master
     * tag_list : []
     * public : false
     * archived : false
     * visibility_level : 0
     * ssh_url_to_repo : git@120.24.222.188:xiaopan/xp-android.git
     * http_url_to_repo : http://120.24.222.188/xiaopan/xp-android.git
     * web_url : http://120.24.222.188/xiaopan/xp-android
     * name : xp-android
     * name_with_namespace : xiaopan / xp-android
     * path : xp-android
     * path_with_namespace : xiaopan/xp-android
     * issues_enabled : true
     * merge_requests_enabled : true
     * wiki_enabled : true
     * builds_enabled : true
     * snippets_enabled : false
     * container_registry_enabled : true
     * created_at : 2016-08-22T02:43:07.828Z
     * last_activity_at : 2017-01-22T08:25:41.625Z
     * shared_runners_enabled : true
     * creator_id : 1
     * namespace : {"id":26,"name":"xiaopan","path":"xiaopan","owner_id":null,"created_at":"2016-08-19T09:19:11.692Z","updated_at":"2016-08-19T09:19:11.692Z","description":"小盘","avatar":{"url":null},"share_with_group_lock":false,"visibility_level":0}
     * avatar_url : null
     * star_count : 0
     * forks_count : 0
     * open_issues_count : 0
     * public_builds : true
     */

    private int id;
    private String description;
    private String default_branch;
    @SerializedName("public")
    private boolean publicX;
    private boolean archived;
    private int visibility_level;
    private String ssh_url_to_repo;
    private String http_url_to_repo;
    private String web_url;
    private String name;
    private String name_with_namespace;
    private String path;
    private String path_with_namespace;
    private boolean issues_enabled;
    private boolean merge_requests_enabled;
    private boolean wiki_enabled;
    private boolean builds_enabled;
    private boolean snippets_enabled;
    private boolean container_registry_enabled;
    private String created_at;
    private String last_activity_at;
    private boolean shared_runners_enabled;
    private int creator_id;
    private NamespaceBean namespace;
    private Object avatar_url;
    private int star_count;
    private int forks_count;
    private int open_issues_count;
    private boolean public_builds;
    private List<?> tag_list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefault_branch() {
        return default_branch;
    }

    public void setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
    }

    public boolean isPublicX() {
        return publicX;
    }

    public void setPublicX(boolean publicX) {
        this.publicX = publicX;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getVisibility_level() {
        return visibility_level;
    }

    public void setVisibility_level(int visibility_level) {
        this.visibility_level = visibility_level;
    }

    public String getSsh_url_to_repo() {
        return ssh_url_to_repo;
    }

    public void setSsh_url_to_repo(String ssh_url_to_repo) {
        this.ssh_url_to_repo = ssh_url_to_repo;
    }

    public String getHttp_url_to_repo() {
        return http_url_to_repo;
    }

    public void setHttp_url_to_repo(String http_url_to_repo) {
        this.http_url_to_repo = http_url_to_repo;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_with_namespace() {
        return name_with_namespace;
    }

    public void setName_with_namespace(String name_with_namespace) {
        this.name_with_namespace = name_with_namespace;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath_with_namespace() {
        return path_with_namespace;
    }

    public void setPath_with_namespace(String path_with_namespace) {
        this.path_with_namespace = path_with_namespace;
    }

    public boolean isIssues_enabled() {
        return issues_enabled;
    }

    public void setIssues_enabled(boolean issues_enabled) {
        this.issues_enabled = issues_enabled;
    }

    public boolean isMerge_requests_enabled() {
        return merge_requests_enabled;
    }

    public void setMerge_requests_enabled(boolean merge_requests_enabled) {
        this.merge_requests_enabled = merge_requests_enabled;
    }

    public boolean isWiki_enabled() {
        return wiki_enabled;
    }

    public void setWiki_enabled(boolean wiki_enabled) {
        this.wiki_enabled = wiki_enabled;
    }

    public boolean isBuilds_enabled() {
        return builds_enabled;
    }

    public void setBuilds_enabled(boolean builds_enabled) {
        this.builds_enabled = builds_enabled;
    }

    public boolean isSnippets_enabled() {
        return snippets_enabled;
    }

    public void setSnippets_enabled(boolean snippets_enabled) {
        this.snippets_enabled = snippets_enabled;
    }

    public boolean isContainer_registry_enabled() {
        return container_registry_enabled;
    }

    public void setContainer_registry_enabled(boolean container_registry_enabled) {
        this.container_registry_enabled = container_registry_enabled;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLast_activity_at() {
        return last_activity_at;
    }

    public void setLast_activity_at(String last_activity_at) {
        this.last_activity_at = last_activity_at;
    }

    public boolean isShared_runners_enabled() {
        return shared_runners_enabled;
    }

    public void setShared_runners_enabled(boolean shared_runners_enabled) {
        this.shared_runners_enabled = shared_runners_enabled;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public NamespaceBean getNamespace() {
        return namespace;
    }

    public void setNamespace(NamespaceBean namespace) {
        this.namespace = namespace;
    }

    public Object getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(Object avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getStar_count() {
        return star_count;
    }

    public void setStar_count(int star_count) {
        this.star_count = star_count;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getOpen_issues_count() {
        return open_issues_count;
    }

    public void setOpen_issues_count(int open_issues_count) {
        this.open_issues_count = open_issues_count;
    }

    public boolean isPublic_builds() {
        return public_builds;
    }

    public void setPublic_builds(boolean public_builds) {
        this.public_builds = public_builds;
    }

    public List<?> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<?> tag_list) {
        this.tag_list = tag_list;
    }

    public static class NamespaceBean {
        /**
         * id : 26
         * name : xiaopan
         * path : xiaopan
         * owner_id : null
         * created_at : 2016-08-19T09:19:11.692Z
         * updated_at : 2016-08-19T09:19:11.692Z
         * description : 小盘
         * avatar : {"url":null}
         * share_with_group_lock : false
         * visibility_level : 0
         */

        private int id;
        private String name;
        private String path;
        private Object owner_id;
        private String created_at;
        private String updated_at;
        private String description;
        private AvatarBean avatar;
        private boolean share_with_group_lock;
        private int visibility_level;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Object getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(Object owner_id) {
            this.owner_id = owner_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public AvatarBean getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBean avatar) {
            this.avatar = avatar;
        }

        public boolean isShare_with_group_lock() {
            return share_with_group_lock;
        }

        public void setShare_with_group_lock(boolean share_with_group_lock) {
            this.share_with_group_lock = share_with_group_lock;
        }

        public int getVisibility_level() {
            return visibility_level;
        }

        public void setVisibility_level(int visibility_level) {
            this.visibility_level = visibility_level;
        }

        public static class AvatarBean {
            /**
             * url : null
             */

            private Object url;

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }
        }
    }
}
