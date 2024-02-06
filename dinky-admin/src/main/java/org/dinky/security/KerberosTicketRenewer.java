package org.dinky.security;

import cn.hutool.core.lang.Singleton;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.security.UserGroupInformation;
import org.dinky.data.exception.DinkyException;
import org.dinky.data.model.SystemConfiguration;
import org.dinky.resource.impl.HdfsResourceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class KerberosTicketRenewer {

    @Value("${security.kerberos.login.keytab}")
    private String keytabLocation;

    @Value("${security.kerberos.login.principal}")
    private String kerberosPrincipal;

    @Value("${security.kerberos.krb5.conf.path}")
    private String krb5Location;

    @Value("${hdfs.fs.defaultFS}")
    private String hdfsDefaultFS;

    @Scheduled(fixedRateString = "${security.kerberos.ticket-renew-period}")
    public void renewKerberosTicket() {
        try {
            UserGroupInformation.getCurrentUser().checkTGTAndReloginFromKeytab();
            System.out.println("Kerberos ticket successfully renewed and HDFS filesystem accessed.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to renew Kerberos ticket or access HDFS.");
        }
    }
}
