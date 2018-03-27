package club.zhcs.thunder.dto;

/**
 * 
 * @author kerbores@gmail.com
 *
 * @param <T>
 */
public class ApiRequest<T> {

	private T data;

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

}
