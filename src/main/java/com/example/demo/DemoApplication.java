package com.example.demo;

import com.example.demo.global.DeviceInfo;
import com.example.demo.global.Global;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@MapperScan(value={"com.example.demo.mapper"})
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DemoApplication.class, args);

		// Run CoAP Server
		new CoAPServerBase();

		// Process user Event
		while (true) {

			// Get Device ID
			DeviceInfo device = Global.device_list.get(Global.deviceId);
			if (device == null) {
				Thread.sleep(1000);
				continue;
			}

			//System.out.println(device.getDeviceID());

			if (Global.getTemperatures() != null) {
				int i = 0;
				int[] numbers = Global.getTemperatures();

				while (i < 5) {
					int input = numbers[i];

					device.TemperatureEvent(input);
					i++;

					Thread.sleep(1000);
				}

				Thread.sleep(5000);
			}

		}

	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource)throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/*.xml"));

		return sessionFactory.getObject();
	}

}
