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
	
	private PublishSubject<Float> subjLeftPower = PublishSubject.create();
	private PublishSubject<Float> subjRightPower = PublishSubject.create();
	
	public Robot() {
		super();
	}
	
	public void setEnginesPower(final Float pLeftPow, final Float pRightPow) {
		this.subjLeftPower.onNext(Math.min(pLeftPow, 1.0f));
		this.subjRightPower.onNext(Math.min(pRightPow, 1.0f));
	}
	
	public Observable<Float> getLeftPower() {
		return this.subjLeftPower;
	}
	
	public Observable<Float> getRightPower() {
		return this.subjRightPower;
	}
}
