sdir=`dirname $0`

$sdir/adb-uninstall-all.sh $*
$sdir/adb-install-all-armv6.sh $*

