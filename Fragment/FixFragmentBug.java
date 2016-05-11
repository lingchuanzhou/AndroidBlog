public abstract class FixFragmentBug extends Fragment {

	protected View view;

	// �ص�����:���� ��Fragment�Ƴ�ʱ
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (view != null) {
			// ��view�����Ĳ�������ʹ�ô����Ƴ�
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)// �Ѿ����ŵ�����
			{
				parent.removeView(view);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (view == null) {
			view = createView(inflater, container, savedInstanceState);
		}
		return view;
	}

	public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

}
