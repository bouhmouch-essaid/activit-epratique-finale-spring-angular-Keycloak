package com.keepllly.auth.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Keepllyauth.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private ExpirationsTime expirationsTime;

    public ExpirationsTime getExpirationsTime() {
        return expirationsTime;
    }

    public void setExpirationsTime(ExpirationsTime expirationsTime) {
        this.expirationsTime = expirationsTime;
    }

    public static class ExpirationsTime{
        private int trial;
        private int essential;

        public int getTrial() {
            return trial;
        }

        public void setTrial(int trial) {
            this.trial = trial;
        }

        public int getEssential() {
            return essential;
        }

        public void setEssential(int essential) {
            this.essential = essential;
        }
    }

    private Mail mail;

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public static class Mail {

        private String host;
        private int port;
        private String username;
        private String password;
        private boolean tls;
        private boolean auth;
        private boolean starttlsEnable;
        private String sslTrust;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isTls() {
            return tls;
        }

        public void setTls(boolean tls) {
            this.tls = tls;
        }

        public boolean isAuth() {
            return auth;
        }

        public void setAuth(boolean auth) {
            this.auth = auth;
        }

        public boolean isStarttlsEnable() {
            return starttlsEnable;
        }

        public void setStarttlsEnable(boolean starttlsEnable) {
            this.starttlsEnable = starttlsEnable;
        }

        public String getSslTrust() {
            return sslTrust;
        }

        public void setSslTrust(String sslTrust) {
            this.sslTrust = sslTrust;
        }
    }

    // jhipster-needle-application-properties-property
    private Realms realms;

    // jhipster-needle-application-properties-property-getter

    public Realms getRealms() {
        return realms;
    }

    public void setRealms(Realms realms) {
        this.realms = realms;
    }

    // jhipster-needle-application-properties-property-class
    public static class Realms {

        private Keeplly keeplly;

        public Keeplly getKeeplly() {
            return keeplly;
        }

        public void setKeeplly(Keeplly keeplly) {
            this.keeplly = keeplly;
        }

        public static class Keeplly {

            private String realmName;

            private String clientId;
            private String clientSecret;
            private String grantType;

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getClientSecret() {
                return clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }

            public String getGrantType() {
                return grantType;
            }

            public void setGrantType(String grantType) {
                this.grantType = grantType;
            }

            public String getRealmName() {
                return realmName;
            }

            public void setRealmName(String realmName) {
                this.realmName = realmName;
            }

            private Google google;

            public Google getGoogle() {
                return google;
            }

            public void setGoogle(Google google) {
                this.google = google;
            }

            public static class Google {

                private String grantType;
                private String subjectTokenType;
                private String clientId;
                private String clientSecret;
                private String subjectIssuer;

                public Google() {}

                public Google(String grantType, String subjectTokenType, String clientId, String clientSecret, String subjectIssuer) {
                    this.grantType = grantType;
                    this.subjectTokenType = subjectTokenType;
                    this.clientId = clientId;
                    this.clientSecret = clientSecret;
                    this.subjectIssuer = subjectIssuer;
                }

                public String getGrantType() {
                    return grantType;
                }

                public void setGrantType(String grantType) {
                    this.grantType = grantType;
                }

                public String getSubjectTokenType() {
                    return subjectTokenType;
                }

                public void setSubjectTokenType(String subjectTokenType) {
                    this.subjectTokenType = subjectTokenType;
                }

                public String getClientId() {
                    return clientId;
                }

                public void setClientId(String clientId) {
                    this.clientId = clientId;
                }

                public String getClientSecret() {
                    return clientSecret;
                }

                public void setClientSecret(String clientSecret) {
                    this.clientSecret = clientSecret;
                }

                public String getSubjectIssuer() {
                    return subjectIssuer;
                }

                public void setSubjectIssuer(String subjectIssuer) {
                    this.subjectIssuer = subjectIssuer;
                }
            }
        }

        private Pactum pactum;

        public Pactum getPactum() {
            return pactum;
        }

        public void setPactum(Pactum pactum) {
            this.pactum = pactum;
        }

        public static class Pactum {

            private String realmName;

            private String clientId;
            private String clientSecret;
            private String grantType;

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getClientSecret() {
                return clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }

            public String getGrantType() {
                return grantType;
            }

            public void setGrantType(String grantType) {
                this.grantType = grantType;
            }

            public String getRealmName() {
                return realmName;
            }

            public void setRealmName(String realmName) {
                this.realmName = realmName;
            }

            private Google google;

            public Google getGoogle() {
                return google;
            }

            public void setGoogle(Google google) {
                this.google = google;
            }

            public static class Google {

                private String grantType;
                private String subjectTokenType;
                private String clientId;
                private String clientSecret;
                private String subjectIssuer;

                public Google() {}

                public Google(String grantType, String subjectTokenType, String clientId, String clientSecret, String subjectIssuer) {
                    this.grantType = grantType;
                    this.subjectTokenType = subjectTokenType;
                    this.clientId = clientId;
                    this.clientSecret = clientSecret;
                    this.subjectIssuer = subjectIssuer;
                }

                public String getGrantType() {
                    return grantType;
                }

                public void setGrantType(String grantType) {
                    this.grantType = grantType;
                }

                public String getSubjectTokenType() {
                    return subjectTokenType;
                }

                public void setSubjectTokenType(String subjectTokenType) {
                    this.subjectTokenType = subjectTokenType;
                }

                public String getClientId() {
                    return clientId;
                }

                public void setClientId(String clientId) {
                    this.clientId = clientId;
                }

                public String getClientSecret() {
                    return clientSecret;
                }

                public void setClientSecret(String clientSecret) {
                    this.clientSecret = clientSecret;
                }

                public String getSubjectIssuer() {
                    return subjectIssuer;
                }

                public void setSubjectIssuer(String subjectIssuer) {
                    this.subjectIssuer = subjectIssuer;
                }
            }
        }
    }

    private Map<String, ServiceConfig> services;

    public Map<String, ServiceConfig> getServices() {
        return services;
    }

    public void setServices(Map<String, ServiceConfig> services) {
        this.services = services;
    }

    public static class ServiceConfig {

        private String serviceName;
        private String host;
        private String port;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }
}
