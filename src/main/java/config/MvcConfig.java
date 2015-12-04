package config;

import org.ranestar.common.CommonSession;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * MVC 설정용 클래스.
 * 이 클래스는 스프링의 sevlet-context.xml 의 역할을 대신한다.
 *
 */
@Configuration
@ComponentScan(basePackages="org.ranestar.web", useDefaultFilters = false, 
				includeFilters = {@Filter(type = FilterType.ANNOTATION, value = Controller.class),
										@Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class) })
@EnableWebMvc
@EnableAsync // @Async 어노테이션을 사용하기 위함
public class MvcConfig extends WebMvcConfigurerAdapter {
// 인터셉터를 추가하기 위해 WebMvcConfigurerAdapter 를 상속한다
	
	/*
     * 프로퍼티 홀더는 다른 빈들이 사용하는 프로퍼티들을 로딩하기 때문에, static 메소드로 실행된다.
     * 다른 일반 빈들이 만들어지기전에 먼저 만들어져야 한다.
     * @return
     */
    @Bean
    public static PropertyPlaceholderConfigurer properties() {
      PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();

      Resource[] resources =
          new ClassPathResource[] {new ClassPathResource("configuration/path.properties")};
      ppc.setLocations(resources);
      ppc.setIgnoreResourceNotFound(false);
      ppc.setIgnoreUnresolvablePlaceholders(false);

      return ppc;
    }
    
	/**
	 * CSS / JavaScript / Image 등의 정적 리소스를 처리해주는 핸들러를 등록
	 */
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	/**
	 * JSP를 뷰로 사용하는 뷰 리졸버 등록
	 */
    @Bean
    public ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    @Bean
    public BeanNameViewResolver beanViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(0);
        return resolver;
    }
    
	/**
	 * message source 들을 등록함
	 */
	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/message", "classpath:messages/validation");
		// if true, the key of the message will be displayed if the key is not
		// found, instead of throwing a NoSuchMessageException
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : never reload, 0 always reload
		messageSource.setCacheSeconds(0);
		return messageSource;
	}
	
	/**
	 * locale resolver
	 */
	@Bean
	public LocaleResolver localeResolver() {

		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		//SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("ko"));
		return cookieLocaleResolver;
	}
	
	/**
	 * 인터셉터 (요청을 가로챔)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(new CommonSession()).addPathPatterns("/work");

	}
	
	@Override
	public void addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {

		// 특별히 controller 를 타지 않아도 되는 뷰만 있는 경우 등록
		// ex) 디자인만 입힌 것들.
		//registry.addViewController("/simpleView").setViewName("/simpleView");


		// 404 오류가 발생했을때 보여줄 뷰를 등록
		// registry.addViewController("/page-not-found").setViewName("errors/404");
	}

	
}