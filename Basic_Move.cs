using UnityEngine;
using System.Collections;

public class Basic_Move : MonoBehaviour {
	public int Speed;
	public GameObject player;
	private Vector3 offset;
	//private GameObject target;
	//private bool mouseState;

	//Camera _mainCam = null;
	// Use this for initialization
	void Start () {
		
	}

	// Update is called once per frame
	void Update () {
		//onMouseDown ();
		//if(Input.anyKeyDown) {
		//	Debug.Log("Pressed left click.");
		//}
		if (Input.GetMouseButtonDown (0)) {
			Debug.Log("Pressed left click.");

			target = GetClickedObject();
			/*
			if (target != null && true == target.Equals (gameObject)) {

				mouseState = true;
				Debug.Log("kkkkkkkkk!!");
			}
			*/
				
		} else if (Input.GetMouseButtonUp (0)) {
			mouseState = false;
		}
		/*
		if (true == mouseState) {
			//clicked
			Debug.Log("clicked ok!!");
			GUI.Box(new Rect(10, 10, 100, 90), "Loader Menu");

			if (GUI.Button (new Rect (20, 40, 80, 20), "Level 1")) {
				Application.LoadLevel(1);
			}

			if (GUI.Button (new Rect (20, 70, 80, 20), "Level 2")) {
				Application.LoadLevel(2);
			}
		} else {
			
		}
*/
		/*
		if (Input.GetKey (KeyCode.RightArrow)) {
			transform.Translate (Vector3.right * Speed * Time.deltaTime);
		}
		if (Input.GetKey (KeyCode.UpArrow)) {
			transform.Translate (Vector3.forward * Speed * Time.deltaTime);
		}
		if (Input.GetKey (KeyCode.DownArrow)) {
			transform.Translate (Vector3.back * Speed * Time.deltaTime);
		}
		*/
		transform.Translate (new Vector3 (0.0f, 0.0f, 1.0f) * Time.deltaTime);
		//transform.position += new Vector3(0.0f, 0.0f, 1.0f);
	}


	/*
	private void onMouseDown() {
		Destroy (gameObject);
	}
*/
	//private GameObject GetClickedObject() {
	//	RaycastHit hit;
	//	GameObject target = null;

	//	Ray ray = _mainCam.ScreenPointToRay (Input.mousePosition);

	//	if (true == (Physics.Raycast (ray.origin, ray.direction * 10, out hit))) {
	//		target = hit.collider.gameObject;
	//	}
	//	return target;

	//}

}

