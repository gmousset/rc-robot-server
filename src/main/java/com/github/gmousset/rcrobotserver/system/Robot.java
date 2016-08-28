/**
 * 
 */
package com.github.gmousset.rcrobotserver.system;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * @author gwendalmousset
 *
 */
public class Robot {
	private final Integer maxValue = 255;
	private PublishSubject<Integer> subjLeftPower = PublishSubject.create();
	private PublishSubject<Integer> subjRightPower = PublishSubject.create();
	
	public Robot() {
		super();
	}
	
	public void setEnginesPower(final Float pLeftPow, final Float pRightPow) {
		final int leftValue = Math.round(pLeftPow * 255);
		final int rightValue = Math.round(pRightPow * 255);
		this.subjLeftPower.onNext(Math.min(this.maxValue, leftValue));
		this.subjRightPower.onNext(Math.min(this.maxValue, rightValue));
	}
	
	public Observable<Integer> getLeftPower() {
		return this.subjLeftPower;
	}
	
	public Observable<Integer> getRightPower() {
		return this.subjRightPower;
	}
}
